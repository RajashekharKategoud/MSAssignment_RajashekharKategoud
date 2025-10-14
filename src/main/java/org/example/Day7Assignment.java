package org.example;

import org.example.Day4AssignmentHelper.ExchangeRateResponse;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Day 7 Assignment: Java 8 - Asynchronous Programming and CompletableFutures
 */
public final class Day7Assignment {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    static void setExecutorForTesting(ExecutorService customExecutor) {
        if (customExecutor != null) {
            executor.shutdownNow();
            executor = customExecutor;
        }
    }

    private Day7Assignment() {
        // utility class, prevent instantiation
    }

    public static void main(String[] args) throws Exception {
        List<String> sources = Arrays.asList("ExchangeA", "ExchangeB", "ExchangeC", "ExchangeD", "ExchangeE");

        System.out.println("---- Q1: Fastest exchange rate ----");
        fetchFastestRate(sources, "USD", "INR");

        System.out.println("\n---- Q2: Aggregated exchange rates ----");
        fetchAllRates(sources, "USD", "INR");

        executor.shutdown();
    }

    /**
     * Q1: Get the fastest response among all exchanges
     */
    private static void fetchFastestRate(List<String> sources, String from, String to) throws Exception {
        List<CompletableFuture<ExchangeRateResponse>> futures = sources.stream()
                .map(src -> CompletableFuture.supplyAsync(() -> fetchRateFromSource(src, from, to), executor))
                .collect(Collectors.toList());

        CompletableFuture<Object> fastest = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0]));

        ExchangeRateResponse response = (ExchangeRateResponse) fastest.get();
        System.out.println("Fastest response: " + response);
    }

    /**
     * Q2: Aggregate all responses and print as JSON
     */
    private static void fetchAllRates(List<String> sources, String from, String to) throws Exception {
        List<CompletableFuture<ExchangeRateResponse>> futures = sources.stream()
                .map(src -> CompletableFuture.supplyAsync(() -> fetchRateFromSource(src, from, to), executor))
                .collect(Collectors.toList());

        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<Map<String, Double>> aggregated = allDone.thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toMap(ExchangeRateResponse::getSource, ExchangeRateResponse::getRate)));

        Map<String, Double> result = aggregated.get();
        System.out.println("Aggregated JSON: " + toJson(result));
    }

    /**
     * Simulate fetching exchange rate from a remote API
     */
    private static ExchangeRateResponse fetchRateFromSource(String source, String from, String to) {
        try {
            int delay = new Random().nextInt(2000) + 500; // 0.5s - 2.5s delay
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        double rate = 80 + new Random().nextDouble() * 10; // Random exchange rate between 80-90
        return new ExchangeRateResponse(source, rate);
    }

    /**
     * Convert Map to simple JSON string
     */
    private static String toJson(Map<String, Double> map) {
        return map.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\": " + e.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
