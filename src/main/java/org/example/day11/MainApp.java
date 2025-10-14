package org.example.day11;

import org.example.day11.context.SumCalculator;
import org.example.day11.factory.SumStrategyFactory;
import org.example.day11.strategy.StrategyType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public final class MainApp {

    private MainApp() { /* Utility class */ }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("Select sum option:");
        System.out.println("1. All Numbers");
        System.out.println("2. Odd Numbers");
        System.out.println("3. Even Numbers");
        System.out.println("4. Prime Numbers");

        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            StrategyType type = mapChoiceToType(choice);

            SumCalculator calculator = new SumCalculator(
                    SumStrategyFactory.getStrategy(type)
            );

            int result = calculator.calculate(numbers);
            System.out.println("Sum (" + type + ") = " + result);
        }
    }

    private static StrategyType mapChoiceToType(int choice) {
        switch (choice) {
            case 2:
                return StrategyType.ODD;
            case 3:
                return StrategyType.EVEN;
            case 4:
                return StrategyType.PRIME;
            case 1:
            default:
                return StrategyType.ALL;
        }
    }
}
