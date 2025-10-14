package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class Day2AssignmentTest {

    @Test
    @DisplayName("Should round BigDecimal correctly when within precision and scale")
    void testRoundToPrecisionScale_ValidInput() {
        BigDecimal input = new BigDecimal("12345.6789");
        Optional<BigDecimal> result = Day2Assignment.roundToPrecisionScale(input, 10, 2);

        assertTrue(result.isPresent(), "Expected result to be present");
        assertEquals(new BigDecimal("12345.68"), result.get());
    }

    @Test
    @DisplayName("Should return empty Optional when precision exceeds limit")
    void testRoundToPrecisionScale_ExceedsPrecision() {
        BigDecimal input = new BigDecimal("1234567890123.45");
        Optional<BigDecimal> result = Day2Assignment.roundToPrecisionScale(input, 10, 2);

        assertFalse(result.isPresent(), "Expected result to be empty due to precision overflow");
    }

    @Test
    @DisplayName("Should handle null input gracefully")
    void testRoundToPrecisionScale_NullInput() {
        Optional<BigDecimal> result = Day2Assignment.roundToPrecisionScale(null, 10, 2);
        assertFalse(result.isPresent(), "Expected empty optional for null input");
    }

    @Test
    @DisplayName("Should handle rounding with different scales")
    void testRoundToPrecisionScale_DifferentScale() {
        BigDecimal input = new BigDecimal("10.12345");
        Optional<BigDecimal> result = Day2Assignment.roundToPrecisionScale(input, 10, 3);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("10.123"), result.get());
    }

    @Test
    @DisplayName("Should verify precision calculation correctly")
    void testRoundToPrecisionScale_PrecisionCheck() {
        BigDecimal input = new BigDecimal("999999.99");
        Optional<BigDecimal> result = Day2Assignment.roundToPrecisionScale(input, 8, 2);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("999999.99"), result.get());
    }

    @Test
    @DisplayName("Should demonstrate difference between double and BigDecimal precision")
    void testDemonstrateDoubleVsBigDecimal() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("demonstrateDoubleVsBigDecimal");
        method.setAccessible(true);
        assertDoesNotThrow(() -> method.invoke(null));
    }

    @Test
    @DisplayName("Should correctly compare equal BigDecimals")
    void testCompareEquals_EqualValues() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("compareEquals", BigDecimal.class, BigDecimal.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(null, new BigDecimal("1.00"), new BigDecimal("1.0"));
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when one BigDecimal is null")
    void testCompareEquals_NullInput() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("compareEquals", BigDecimal.class, BigDecimal.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(null, null, new BigDecimal("1.0"));
        assertFalse(result);
    }

    @Test
    @DisplayName("Should format BigDecimal with given scale correctly")
    void testFormattedWithScale() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("formattedWithScale", BigDecimal.class, int.class);
        method.setAccessible(true);
        String result = (String) method.invoke(null, new BigDecimal("123.45000"), 2);
        assertEquals("123.45", result);
    }

    @Test
    @DisplayName("Should handle invalid BigDecimal parsing gracefully")
    void testParseToBigDecimal_Invalid() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("parseToBigDecimal", String.class);
        method.setAccessible(true);
        Optional<BigDecimal> result = (Optional<BigDecimal>) method.invoke(null, "invalid123");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should parse valid BigDecimal string correctly")
    void testParseToBigDecimal_Valid() throws Exception {
        Method method = Day2Assignment.class.getDeclaredMethod("parseToBigDecimal", String.class);
        method.setAccessible(true);
        Optional<BigDecimal> result = (Optional<BigDecimal>) method.invoke(null, "123.45");
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("123.45"), result.get());
    }

    @Test
    @DisplayName("Utility class should have private constructor")
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<Day2Assignment> constructor =
                Day2Assignment.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertDoesNotThrow(() -> constructor.newInstance());

    }

    @Test
    @DisplayName("Should run main method without exceptions")
    void testMainMethod() {
        assertDoesNotThrow(() -> Day2Assignment.main(new String[]{}));
    }
}
