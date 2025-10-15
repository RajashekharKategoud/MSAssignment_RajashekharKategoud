package org.example.day11.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvenSumStrategyTest {

    @Test
    void shouldCalculateSumForEvenNumbers() {
        EvenSumStrategy strategy = new EvenSumStrategy();
        int result = strategy.calculateSum(Arrays.asList(1, 2, 3, 4, 6));
        assertEquals(12, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        EvenSumStrategy strategy = new EvenSumStrategy();
        assertEquals(0, strategy.calculateSum(Collections.emptyList()));
    }
}
