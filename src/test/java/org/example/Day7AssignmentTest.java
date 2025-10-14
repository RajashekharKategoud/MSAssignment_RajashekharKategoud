package org.example;

import org.example.Day4AssignmentHelper.ExchangeRateResponse;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day7Assignment Test - 100% Code Coverage")
class Day7AssignmentTest {

    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));

        // Ensure a fresh executor for every test
        ExecutorService freshExecutor = Executors.newFixedThreadPool(5);
        Day7Assignment.setExecutorForTesting(freshExecutor);
    }



    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }

    @Test
    @DisplayName("Should execute main() and cover all methods")
    void testMainExecution() throws Exception {
        Day7Assignment.main(new String[]{});

        String output = outputStream.toString().toLowerCase();

        assertAll(
                () -> assertTrue(output.contains("q1"), "Missing Q1 output"),
                () -> assertTrue(output.contains("fastest response"), "Missing fastest response output"),
                () -> assertTrue(output.contains("q2"), "Missing Q2 output"),
                () -> assertTrue(output.contains("aggregated json"), "Missing aggregated JSON output"),
                () -> assertTrue(output.contains("exchange"), "Missing exchange names in output")
        );
    }

    @Test
    @DisplayName("Should correctly fetch fastest rate using reflection")
    void testFetchFastestRate() throws Exception {
        List<String> sources = Arrays.asList("ExchangeX", "ExchangeY", "ExchangeZ");

        Method method = Day7Assignment.class.getDeclaredMethod(
                "fetchFastestRate", List.class, String.class, String.class);
        method.setAccessible(true);

        method.invoke(null, sources, "USD", "INR");

        String output = outputStream.toString().toLowerCase();
        assertTrue(output.contains("fastest response"), "Fastest response not printed");
    }

    @Test
    @DisplayName("Should correctly aggregate all rates using reflection")
    void testFetchAllRates() throws Exception {
        List<String> sources = Arrays.asList("Ex1", "Ex2", "Ex3");

        Method method = Day7Assignment.class.getDeclaredMethod(
                "fetchAllRates", List.class, String.class, String.class);
        method.setAccessible(true);
        method.invoke(null, sources, "USD", "INR");

        String output = outputStream.toString().toLowerCase();
        assertAll(
                () -> assertTrue(output.contains("aggregated json"), "Aggregated output missing"),
                () -> assertTrue(output.contains("{"), "JSON format missing"),
                () -> assertTrue(output.contains("ex1"), "Source missing in JSON")
        );
    }

    @Test
    @DisplayName("Should correctly simulate fetching exchange rate")
    void testFetchRateFromSource() throws Exception {
        Method method = Day7Assignment.class.getDeclaredMethod(
                "fetchRateFromSource", String.class, String.class, String.class);
        method.setAccessible(true);

        ExchangeRateResponse response =
                (ExchangeRateResponse) method.invoke(null, "MockExchange", "USD", "INR");

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals("MockExchange", response.getSource(), "Source mismatch"),
                () -> assertTrue(response.getRate() >= 80 && response.getRate() <= 90, "Rate should be within 80–90 range")
        );
    }

    @Test
    @DisplayName("Should correctly convert map to JSON string")
    void testToJson() throws Exception {
        Method method = Day7Assignment.class.getDeclaredMethod("toJson", Map.class);
        method.setAccessible(true);

        Map<String, Double> sample = new LinkedHashMap<>();
        sample.put("ExchangeA", 82.5);
        sample.put("ExchangeB", 84.3);

        String json = (String) method.invoke(null, sample);

        assertAll(
                () -> assertTrue(json.startsWith("{")),
                () -> assertTrue(json.endsWith("}")),
                () -> assertTrue(json.contains("ExchangeA")),
                () -> assertTrue(json.contains("ExchangeB")),
                () -> assertTrue(json.contains(":"))
        );
    }

    @Test
    @DisplayName("Should cover private constructor using reflection")
    void testPrivateConstructor() throws Exception {
        Constructor<Day7Assignment> constructor = Day7Assignment.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Day7Assignment instance = constructor.newInstance();
        assertNotNull(instance, "Instance should not be null");
    }

    @Test
    @DisplayName("Should handle InterruptedException in fetchRateFromSource gracefully")
    void testInterruptedThreadHandling() throws Exception {
        Method method = Day7Assignment.class.getDeclaredMethod(
                "fetchRateFromSource", String.class, String.class, String.class);
        method.setAccessible(true);

        Thread.currentThread().interrupt(); // simulate interrupt
        ExchangeRateResponse response =
                (ExchangeRateResponse) method.invoke(null, "InterruptedSource", "USD", "INR");

        assertNotNull(response, "Response should still be returned even after interrupt");
        assertTrue(Thread.currentThread().isInterrupted(), "Interrupt flag should remain set (expected true)");
    }
}
