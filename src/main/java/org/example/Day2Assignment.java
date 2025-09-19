package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Day 2 assignment: round BigDecimal to (precision, scale), print result and compare with expected.
 */
public final class Day2Assignment {

    private Day2Assignment() { /* utility class */ }

    public static void main(String[] args) {
        // First case should PASS
        runExample("12345.12345467", "12345.12345", 25, 5);

        // Second case should FAIL (wrong expected value on purpose)
        runExample("12345.123456", "12345.12345", 25, 5);

        // Third case: Overflow (precision exceeded)
        runExample("12345678901234567890.12345", "12345678901234567890.12345", 25, 5);

        // Show Double vs BigDecimal
        demonstrateDoubleVsBigDecimal();
    }

    private static void runExample(String input, String expectedStr, int precision, int scale) {
        BigDecimal value = parseToBigDecimal(input)
                .orElseThrow(() -> new IllegalArgumentException("invalid input"));
        BigDecimal expected = parseToBigDecimal(expectedStr)
                .orElseThrow(() -> new IllegalArgumentException("invalid expected"));
        Optional<BigDecimal> roundedOpt = roundToPrecisionScale(value, precision, scale);

        System.out.println("Input   : " + input);
        System.out.println("Expected: " + expectedStr);

        if (!roundedOpt.isPresent()) {
            System.out.println("Result  : <overflow or cannot fit into precision " + precision + " and scale " + scale + ">");
            System.out.println("Compare : FAILED (no valid rounded value)");
            System.out.println("----");
            return;
        }

        BigDecimal rounded = roundedOpt.get();
        System.out.println("Result  : " + formattedWithScale(rounded, scale));
        System.out.println("Compare : " + (compareEquals(rounded, expected) ? "PASSED" : "FAILED"));
        System.out.println("----");
    }

    private static Optional<BigDecimal> parseToBigDecimal(String s) {
        if (s == null) return Optional.empty();
        try {
            return Optional.of(new BigDecimal(s));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns rounded value if it fits into (precision, scale), otherwise Optional.empty().
     *
     * precision = maximum total number of digits (integer + fraction)
     * scale = number of digits to the right of decimal.
     */
    public static Optional<BigDecimal> roundToPrecisionScale(BigDecimal value, int precision, int scale) {
        if (value == null) return Optional.empty();

        BigDecimal rounded = value.setScale(scale, RoundingMode.HALF_UP);
        int actualPrecision = rounded.precision();

        if (actualPrecision > precision) {
            return Optional.empty();
        }
        return Optional.of(rounded);
    }

    private static boolean compareEquals(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    private static String formattedWithScale(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.UNNECESSARY).toPlainString();
    }

    /**
     * Demonstrates why BigDecimal is preferred over double for precise values.
     */
    private static void demonstrateDoubleVsBigDecimal() {
        double d1 = 0.1;
        double d2 = 0.2;
        double sum = d1 + d2;

        BigDecimal bd1 = new BigDecimal("0.1");
        BigDecimal bd2 = new BigDecimal("0.2");
        BigDecimal bdSum = bd1.add(bd2);

        System.out.println("---- Double vs BigDecimal ----");
        System.out.println("Using double   : 0.1 + 0.2 = " + sum); // may not be exactly 0.3
        System.out.println("Using BigDecimal: 0.1 + 0.2 = " + bdSum.toPlainString()); // exact 0.3
        System.out.println("-------------------------------");
    }
}
