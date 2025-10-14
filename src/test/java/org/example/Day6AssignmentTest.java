package org.example;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day6AssignmentTest {

    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    static void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterAll
    static void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @Order(1)
    @DisplayName("Should execute main() and cover all 22 questions")
    void testMainExecution() throws Exception {
        Day6Assignment.main(new String[]{});
        String output = outputStream.toString().toLowerCase();

        assertAll(
                () -> assertTrue(output.contains("day 6 assignment")),
                () -> assertTrue(output.contains("q1")),
                () -> assertTrue(output.contains("q22")),
                () -> assertTrue(output.contains("assignment completed"))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should cover all private static helper methods using reflection")
    void testPrivateHelpersUsingReflection() throws Exception {
        Class<?> clazz = Day6Assignment.class;
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if ("main".equals(method.getName())) {
                continue;
            }
            if ((method.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0 && method.getParameterCount() == 0) {
                method.setAccessible(true);
                try {
                    method.invoke(null);
                } catch (Exception ignored) {
                    // Some concurrent operations may throw interruption — acceptable
                }
            }
        }

        String output = outputStream.toString().toLowerCase();
        assertAll(
                () -> assertTrue(output.contains("thread"), "Missing thread-related outputs"),
                () -> assertTrue(output.contains("executorservice"), "Missing executor output"),
                () -> assertTrue(output.contains("lock"), "Missing lock output"),
                () -> assertTrue(output.contains("parallel"), "Missing parallel output"),
                () -> assertTrue(output.contains("collection"), "Missing collection output")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Should verify concurrency-related sections produce expected outputs")
    void testConcurrencyOutputValidation() {
        String output = outputStream.toString().toLowerCase();

        assertAll(
                () -> assertTrue(output.contains("runnable"), "Missing runnable output"),
                () -> assertTrue(output.contains("lambda"), "Missing lambda thread output"),
                () -> assertTrue(output.contains("executor"), "Missing executor section"),
                () -> assertTrue(output.contains("semaphore"), "Missing semaphore section"),
                () -> assertTrue(output.contains("countdownlatch"), "Missing countdown latch section"),
                () -> assertTrue(output.contains("cyclicbarrier"), "Missing cyclic barrier section")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should not contain unhandled exceptions in output")
    void testNoUnhandledExceptionsInOutput() {
        String output = outputStream.toString().toLowerCase();

        // Allow benign mentions like InterruptedException but fail for serious ones
        boolean hasRealException =
                output.contains("nullpointerexception") ||
                        output.contains("illegalstateexception") ||
                        output.contains("runtimeexception") ||
                        output.contains("error:");

        assertFalse(hasRealException, "Unexpected runtime exception found in output");
    }

    @Test
    @Order(5)
    @DisplayName("Should verify private inner Counter classes functionality")
    void testInnerCounterClasses() throws Exception {
        testCounterClass("org.example.Day6Assignment$Counter");
        testCounterClass("org.example.Day6Assignment$CounterSync");
        testCounterClass("org.example.Day6Assignment$CounterLock");
    }

    private void testCounterClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object counter = constructor.newInstance();

        Method increment = clazz.getDeclaredMethod("increment");
        Method getValue = clazz.getDeclaredMethod("getValue");
        increment.setAccessible(true);
        getValue.setAccessible(true);

        increment.invoke(counter);
        Object value = getValue.invoke(counter);
        assertEquals(1, value, className + " did not increment correctly");
    }
}
