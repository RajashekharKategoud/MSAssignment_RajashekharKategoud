package org.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.stream.IntStream;
import java.time.LocalTime;
import java.util.function.Consumer;


/**
 * Day 6 Assignment: Concurrency, Executors, ThreadPools, Locks, etc.
 *
 * All code uses Java 8 APIs only.
 */
public final class Day6Assignment {

    private Day6Assignment() { /* utility */ }

    public static void main(String[] args) {
        System.out.println("=== Day 6 Assignment ===");

        // Section 1
        q1_threadRunnablePrint1to5();
        q2_lambdaThreadPrintName5Times();
        q3_threeDownloadThreadsJoin();

        // Section 2
        q4_callableSum1to100();
        q5_fixedThreadPoolReuse();
        q6_cachedThreadPool();
        q7_customThreadPoolExecutorWithRejectedHandler();

        // Section 3
        q8_executorCompletionService();
        q9_shutdownVsShutdownNow();
        q10_submitAfterShutdown();

        // Section 4
        q11_incrementWithoutSync();
        q12_incrementWithSynchronized();
        q13_incrementWithReentrantLock();
        q14_readWriteLockExample();

        // Section 5
        q15_countDownLatch();
        q16_semaphoreExample();
        q17_cyclicBarrierExample();

        // Section 6
        q18_concurrentModificationExceptionDemo();
        q19_threadSafeCollectionsComparison();
        q20_atomicIntegerCounter();

        // Parallelism & Performance
        q21_intStreamParallelPrintThreads();
        q22_benchmarkExecutorVsParallelStream();

        System.out.println("=== Day 6 Assignment Completed ===");
    }

    // ---------------------------
    // Section 1: Threads & Runnable
    // ---------------------------

    // 1. Create and start a Thread using Runnable; print numbers 1..5 with 1s delay.
    private static void q1_threadRunnablePrint1to5() {
        System.out.println("\n-- Q1: Runnable thread printing 1..5 with 1s delay --");
        Thread t = new Thread(new Runnable() {
            @Override public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Runnable thread: " + i);
                    sleepMillis(1000);
                }
            }
        });
        t.start();
        joinQuietly(t);
    }

    // 2. Lambda thread printing current thread name 5 times.
    private static void q2_lambdaThreadPrintName5Times() {
        System.out.println("\n-- Q2: Lambda thread printing thread name 5 times --");
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Lambda thread name: " + Thread.currentThread().getName());
                sleepMillis(200);
            }
        });
        t.start();
        joinQuietly(t);
    }

    // 3. Create 3 threads to simulate file downloads; join() then print done.
    private static void q3_threeDownloadThreadsJoin() {
        System.out.println("\n-- Q3: 3 download threads, use join() --");
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int idx = i + 1;
            threads[i] = new Thread(() -> {
                System.out.println("Download " + idx + " started");
                sleepMillis(600 + idx * 200);
                System.out.println("Download " + idx + " finished");
            });
            threads[i].start();
        }
        for (Thread t : threads) joinQuietly(t);
        System.out.println("All files downloaded");
    }

    // ---------------------------
    // Section 2: Callable, Future & ThreadPool
    // ---------------------------

    // 4. Callable<Integer> sum 1..100 submitted to ExecutorService; get Future result.
    private static void q4_callableSum1to100() {
        System.out.println("\n-- Q4: Callable sum 1..100 using ExecutorService --");
        ExecutorService ex = Executors.newSingleThreadExecutor();
        try {
            Future<Integer> future = ex.submit(new Callable<Integer>() {
                @Override public Integer call() {
                    int sum = 0;
                    for (int i = 1; i <= 100; i++) sum += i;
                    return sum;
                }
            });
            try {
                System.out.println("Sum 1..100 = " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } finally {
            ex.shutdown();
        }
    }

    // 5. Fixed thread pool of 3; submit 10 tasks that print thread name and sleep 2s.
    private static void q5_fixedThreadPoolReuse() {
        System.out.println("\n-- Q5: Executors.newFixedThreadPool(3) submit 10 tasks --");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                final int id = i;
                pool.submit(() -> {
                    System.out.println("FixedPool task " + id + " running on " + Thread.currentThread().getName());
                    sleepMillis(2000);
                });
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 6. Cached thread pool for same 10 tasks; more threads may be created.
    private static void q6_cachedThreadPool() {
        System.out.println("\n-- Q6: Executors.newCachedThreadPool() submit 10 tasks --");
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                final int id = i;
                pool.submit(() -> {
                    System.out.println("CachedPool task " + id + " running on " + Thread.currentThread().getName());
                    sleepMillis(1500);
                });
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 7. Custom ThreadPoolExecutor core=2 max=4 queue=2; submit 6 tasks; handle rejected tasks.
    private static void q7_customThreadPoolExecutorWithRejectedHandler() {
        System.out.println("\n-- Q7: Custom ThreadPoolExecutor with RejectedExecutionHandler --");
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // core
                4, // max
                30L, TimeUnit.SECONDS,
                queue,
                new ThreadPoolExecutor.CallerRunsPolicy() // fallback policy (runs in caller thread)
        );

        try {
            for (int i = 0; i < 6; i++) {
                final int id = i;
                executor.execute(() -> {
                    System.out.println("CustomPool task " + id + " start on " + Thread.currentThread().getName() + " at " + LocalTime.now());
                    sleepMillis(800);
                    System.out.println("CustomPool task " + id + " end on " + Thread.currentThread().getName() + " at " + LocalTime.now());
                });
            }
        } finally {
            executor.shutdown();
            awaitTerminationQuietly(executor);
        }
    }

    // ---------------------------
    // Section 3: ExecutorCompletionService & Shutdown
    // ---------------------------

    // 8. Use ExecutorCompletionService to submit 5 Callables and print results as they complete.
    private static void q8_executorCompletionService() {
        System.out.println("\n-- Q8: ExecutorCompletionService example --");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CompletionService<String> ecs = new ExecutorCompletionService<>(pool);
        try {
            for (int i = 0; i < 5; i++) {
                final int id = i;
                ecs.submit(() -> {
                    sleepMillis(200 + id * 200);
                    return "file-" + id + ".txt";
                });
            }
            for (int i = 0; i < 5; i++) {
                try {
                    Future<String> future = ecs.take(); // blocks until one completes
                    System.out.println("Completed: " + future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 9. Demonstrate difference between shutdown() and shutdownNow().
    private static void q9_shutdownVsShutdownNow() {
        System.out.println("\n-- Q9: shutdown() vs shutdownNow() --");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            pool.submit(() -> {
                System.out.println("Task A started");
                sleepMillis(500);
                System.out.println("Task A finished");
            });
            pool.submit(() -> {
                System.out.println("Task B started");
                sleepMillis(1000);
                System.out.println("Task B finished");
            });

            pool.shutdown(); // allows submitted tasks to finish
            boolean terminated = false;
            try {
                terminated = pool.awaitTermination(300, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("After shutdown: terminated=" + terminated);

            // create another pool to demonstrate shutdownNow
            ExecutorService pool2 = Executors.newFixedThreadPool(2);
            pool2.submit(() -> {
                System.out.println("Long task started");
                sleepMillis(2000);
                System.out.println("Long task finished");
            });
            List<Runnable> neverStarted = pool2.shutdownNow(); // attempts to cancel running tasks and returns not-started
            System.out.println("shutdownNow returned not-started tasks count: " + neverStarted.size());
            awaitTerminationQuietly(pool2);
        } finally {
            // ensure pool is shut at end
        }
    }

    // 10. Submit a task after shutting down; catch RejectedExecutionException.
    private static void q10_submitAfterShutdown() {
        System.out.println("\n-- Q10: Submit after shutdown demonstrates RejectedExecutionException --");
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.shutdown();
        try {
            pool.submit(() -> System.out.println("This won't run"));
        } catch (RejectedExecutionException e) {
            System.out.println("Caught RejectedExecutionException as expected: " + e.getMessage());
        } finally {
            awaitTerminationQuietly(pool);
        }
    }

    // ---------------------------
    // Section 4: Locks & Synchronization
    // ---------------------------

    // 11. Two threads incrementing shared counter without synchronization -> inconsistent results.
    private static void q11_incrementWithoutSync() {
        System.out.println("\n-- Q11: Increment without synchronization (race) --");
        final Counter counter = new Counter();
        Thread t1 = new Thread(() -> runIncrementLoop(counter, 1000));
        Thread t2 = new Thread(() -> runIncrementLoop(counter, 1000));
        t1.start(); t2.start();
        joinQuietly(t1); joinQuietly(t2);
        System.out.println("Final counter (expected 2000): " + counter.getValue());
    }

    // 12. Make it thread-safe with synchronized
    private static void q12_incrementWithSynchronized() {
        System.out.println("\n-- Q12: Increment using synchronized --");
        final CounterSync counter = new CounterSync();
        Thread t1 = new Thread(() -> runIncrementLoopSync(counter, 1000));
        Thread t2 = new Thread(() -> runIncrementLoopSync(counter, 1000));
        t1.start(); t2.start();
        joinQuietly(t1); joinQuietly(t2);
        System.out.println("Final counter (expected 2000): " + counter.getValue());
    }

    // 13. Use ReentrantLock instead
    private static void q13_incrementWithReentrantLock() {
        System.out.println("\n-- Q13: Increment using ReentrantLock --");
        final CounterLock counter = new CounterLock();
        Thread t1 = new Thread(() -> runIncrementLoopLock(counter, 1000));
        Thread t2 = new Thread(() -> runIncrementLoopLock(counter, 1000));
        t1.start(); t2.start();
        joinQuietly(t1); joinQuietly(t2);
        System.out.println("Final counter (expected 2000): " + counter.getValue());
    }

    // 14. ReadWriteLock: 1 writer, multiple readers
    private static void q14_readWriteLockExample() {
        System.out.println("\n-- Q14: ReadWriteLock example --");
        final SharedList shared = new SharedList();
        Thread writer = new Thread(() -> {
            shared.write(s -> {
                for (int i = 0; i < 5; i++) {
                    s.add("item-" + i);
                    System.out.println("Writer added: item-" + i);
                    sleepMillis(200);
                }
            });
        });

        Runnable readerTask = () -> {
            for (int i = 0; i < 5; i++) {
                List<String> snapshot = shared.readCopy();
                System.out.println(Thread.currentThread().getName() + " read: " + snapshot);
                sleepMillis(150);
            }
        };

        Thread r1 = new Thread(readerTask, "Reader-1");
        Thread r2 = new Thread(readerTask, "Reader-2");

        writer.start(); r1.start(); r2.start();
        joinQuietly(writer); joinQuietly(r1); joinQuietly(r2);
    }

    // ---------------------------
    // Section 5: Concurrency Utilities
    // ---------------------------

    // 15. CountDownLatch ensure 3 services initialized before main continues
    private static void q15_countDownLatch() {
        System.out.println("\n-- Q15: CountDownLatch example --");
        final CountDownLatch latch = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 3; i++) {
                final int id = i;
                pool.submit(() -> {
                    System.out.println("Service " + id + " initializing...");
                    sleepMillis(300 + id * 200);
                    System.out.println("Service " + id + " initialized");
                    latch.countDown();
                });
            }
            try {
                latch.await(); // wait for all services to initialize
                System.out.println("All services initialized, main continues");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 16. Semaphore allowing only 2 threads into critical section at a time
    private static void q16_semaphoreExample() {
        System.out.println("\n-- Q16: Semaphore example (permits=2) --");
        final Semaphore sem = new Semaphore(2);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 6; i++) {
                final int id = i;
                pool.submit(() -> {
                    try {
                        sem.acquire();
                        System.out.println("Thread " + id + " acquired semaphore");
                        sleepMillis(400);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        System.out.println("Thread " + id + " releasing semaphore");
                        sem.release();
                    }
                });
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 17. CyclicBarrier for 3 players
    private static void q17_cyclicBarrierExample() {
        System.out.println("\n-- Q17: CyclicBarrier example --");
        final CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All players ready — starting game"));
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 3; i++) {
                final int id = i;
                pool.submit(() -> {
                    System.out.println("Player " + id + " prepping");
                    sleepMillis(200 + id * 200);
                    try {
                        System.out.println("Player " + id + " waiting at barrier");
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // ---------------------------
    // Section 6: Thread-safe Collections & Atomic Variables
    // ---------------------------

    // 18. ConcurrentModificationException demo
    private static void q18_concurrentModificationExceptionDemo() {
        System.out.println("\n-- Q18: ConcurrentModificationException demo --");
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) list.add(i);

        Thread t1 = new Thread(() -> {
            try {
                for (Integer i : list) { // this iteration will fail if list modified concurrently
                    System.out.println("Iterating value: " + i);
                    sleepMillis(100);
                }
            } catch (ConcurrentModificationException ex) {
                System.out.println("Caught ConcurrentModificationException in iterator thread: " + ex);
            }
        });

        Thread t2 = new Thread(() -> {
            sleepMillis(150);
            System.out.println("Modifier thread: removing element");
            list.remove(0); // structural modification
        });

        t1.start(); t2.start();
        joinQuietly(t1); joinQuietly(t2);
    }

    // 19. Fix using synchronizedList and CopyOnWriteArrayList
    private static void q19_threadSafeCollectionsComparison() {
        System.out.println("\n-- Q19: Collections.synchronizedList vs CopyOnWriteArrayList --");
        final List<Integer> base = new ArrayList<>();
        for (int i = 0; i < 100; i++) base.add(i);

        // synchronizedList
        final List<Integer> syncList = Collections.synchronizedList(new ArrayList<>(base));
        long start = System.nanoTime();
        runReadersWriters(syncList);
        long syncDuration = System.nanoTime() - start;
        System.out.println("SynchronizedList duration(ns): " + syncDuration);

        // copyOnWrite
        final List<Integer> cowList = new CopyOnWriteArrayList<>(base);
        start = System.nanoTime();
        runReadersWriters(cowList);
        long cowDuration = System.nanoTime() - start;
        System.out.println("CopyOnWriteArrayList duration(ns): " + cowDuration);
    }

    // helper for readers/writers simulation
    private static void runReadersWriters(final List<Integer> list) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < 8; i++) {
                pool.submit(() -> {
                    for (int j = 0; j < 50; j++) {
                        // readers
                        int size = list.size();
                        if (size > 0) list.get(0);
                    }
                });
            }
            for (int i = 0; i < 2; i++) {
                pool.submit(() -> {
                    for (int j = 0; j < 50; j++) {
                        // writer
                        list.add(new Random().nextInt());
                        sleepMillis(5);
                    }
                });
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
    }

    // 20. Use AtomicInteger for thread-safe counter with 1000 threads
    private static void q20_atomicIntegerCounter() {
        System.out.println("\n-- Q20: AtomicInteger counter with many threads --");
        final AtomicInteger atomic = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            for (int i = 0; i < 1000; i++) {
                pool.submit(() -> atomic.incrementAndGet());
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
            System.out.println("Atomic counter value (expected 1000): " + atomic.get());
        }
    }

    // ---------------------------
    // Parallelism & Performance
    // ---------------------------

    // 21. IntStream.range(1,101).parallel() print current thread for each iteration
    private static void q21_intStreamParallelPrintThreads() {
        System.out.println("\n-- Q21: parallel IntStream processing (1..100) --");
        IntStream.range(1, 101)
                .parallel()
                .forEach(i -> System.out.println("i=" + i + " on " + Thread.currentThread().getName()));
    }

    // 22. Simple benchmark: ExecutorService vs parallelStream for 100 tasks
    private static void q22_benchmarkExecutorVsParallelStream() {
        System.out.println("\n-- Q22: Benchmark ExecutorService vs parallelStream (100 tasks) --");
        final int tasks = 100;
        // Task: sleep 20ms to simulate work
        Callable<Void> work = () -> {
            sleepMillis(20);
            return null;
        };

        // ExecutorService timing
        ExecutorService pool = Executors.newFixedThreadPool(8);
        long start = System.nanoTime();
        try {
            List<Future<Void>> futures = new ArrayList<>();
            for (int i = 0; i < tasks; i++) futures.add(pool.submit(work));
            for (Future<Void> f : futures) {
                try { f.get(); } catch (Exception ignored) {}
            }
        } finally {
            pool.shutdown();
            awaitTerminationQuietly(pool);
        }
        long execDuration = System.nanoTime() - start;
        System.out.println("ExecutorService time (ms): " + (execDuration / 1_000_000));

        // parallelStream timing
        start = System.nanoTime();
        IntStream.range(0, tasks)
                .parallel()
                .forEach(i -> sleepMillis(20));
        long psDuration = System.nanoTime() - start;
        System.out.println("parallelStream time (ms): " + (psDuration / 1_000_000));
    }

    // ---------------------------
    // Helper utilities & small classes
    // ---------------------------

    private static void runIncrementLoop(Counter counter, int times) {
        for (int i = 0; i < times; i++) {
            counter.increment(); // race condition
        }
    }
    private static void runIncrementLoopSync(CounterSync counter, int times) {
        for (int i = 0; i < times; i++) counter.increment();
    }
    private static void runIncrementLoopLock(CounterLock counter, int times) {
        for (int i = 0; i < times; i++) {
            counter.increment();
        }
    }

    private static void sleepMillis(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private static void joinQuietly(Thread t) {
        try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private static void awaitTerminationQuietly(ExecutorService svc) {
        try { if (!svc.awaitTermination(5, TimeUnit.SECONDS)) svc.shutdownNow(); } catch (InterruptedException e) { svc.shutdownNow(); Thread.currentThread().interrupt(); }
    }

    // Counter classes
    private static class Counter { int value = 0; void increment() { value++; } int getValue() { return value; } }
    private static class CounterSync { private int value = 0; synchronized void increment() { value++; } int getValue() { return value; } }
    private static class CounterLock {
        private int value = 0;
        private final ReentrantLock lock = new ReentrantLock();
        void increment() { lock.lock(); try { value++; } finally { lock.unlock(); } }
        int getValue() { return value; }
    }

    // SharedList for ReadWriteLock example
    private static class SharedList {
        private final List<String> list = new ArrayList<>();
        private final ReadWriteLock rw = new ReentrantReadWriteLock();

        void write(Consumer<List<String>> writer) {
            Lock w = rw.writeLock();
            w.lock();
            try { writer.accept(list); } finally { w.unlock(); }
        }

        List<String> readCopy() {
            Lock r = rw.readLock();
            r.lock();
            try { return new ArrayList<>(list); } finally { r.unlock(); }
        }
    }
}
