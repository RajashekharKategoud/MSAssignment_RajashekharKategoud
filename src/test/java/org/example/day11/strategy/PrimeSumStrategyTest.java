package org.example.day11.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimeSumStrategyTest {

    @Test
    void shouldCalculateSumForPrimeNumbers() {
        PrimeSumStrategy strategy = new PrimeSumStrategy();
        int result = strategy.calculateSum(Arrays.asList(1, 2, 3, 4, 5, 7, 9));
        assertEquals(17, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        PrimeSumStrategy strategy = new PrimeSumStrategy();
        assertEquals(0, strategy.calculateSum(Collections.emptyList()));
    }
}
