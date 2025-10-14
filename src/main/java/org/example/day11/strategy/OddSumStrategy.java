package org.example.day11.strategy;

import java.util.List;

public final class OddSumStrategy implements SumStrategy {

    @Override
    public int calculateSum(List<Integer> numbers) {
        return numbers.stream()
                .filter(num -> num % 2 != 0)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
