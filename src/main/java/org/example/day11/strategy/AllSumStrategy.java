package org.example.day11.strategy;

import java.util.List;

public final class AllSumStrategy implements SumStrategy {

    @Override
    public int calculateSum(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
