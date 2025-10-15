package org.example.day11.context;

import org.example.day11.strategy.AllSumStrategy;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumCalculatorTest {

    @Test
    void shouldCalculateSumUsingProvidedStrategy() {
        SumCalculator calculator = new SumCalculator(new AllSumStrategy());
        int result = calculator.calculate(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(15, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        SumCalculator calculator = new SumCalculator(new AllSumStrategy());
        int result = calculator.calculate(Collections.emptyList());
        assertEquals(0, result);
    }
}
