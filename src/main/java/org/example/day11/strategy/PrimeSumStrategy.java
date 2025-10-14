package org.example.day11.strategy;

import java.util.List;

public final class PrimeSumStrategy implements SumStrategy {

    @Override
    public int calculateSum(List<Integer> numbers) {
        return numbers.stream()
                .filter(this::isPrime)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
