package org.example.day11.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllSumStrategyTest {

    @Test
    void shouldCalculateSumForAllNumbers() {
        AllSumStrategy strategy = new AllSumStrategy();
        int result = strategy.calculateSum(Arrays.asList(1, 2, 3, 4));
        assertEquals(10, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        AllSumStrategy strategy = new AllSumStrategy();
        assertEquals(0, strategy.calculateSum(Collections.emptyList()));
    }
}
