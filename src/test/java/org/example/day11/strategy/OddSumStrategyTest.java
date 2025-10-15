package org.example.day11.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OddSumStrategyTest {

    @Test
    void shouldCalculateSumForOddNumbers() {
        OddSumStrategy strategy = new OddSumStrategy();
        int result = strategy.calculateSum(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(9, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        OddSumStrategy strategy = new OddSumStrategy();
        assertEquals(0, strategy.calculateSum(Collections.emptyList()));
    }
}
