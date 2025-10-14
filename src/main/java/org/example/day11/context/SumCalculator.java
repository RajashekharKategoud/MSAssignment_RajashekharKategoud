package org.example.day11.context;

import org.example.day11.strategy.SumStrategy;

import java.util.List;

public final class SumCalculator {

    private final SumStrategy strategy;

    public SumCalculator(SumStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(List<Integer> numbers) {
        return strategy.calculateSum(numbers);
    }
}
