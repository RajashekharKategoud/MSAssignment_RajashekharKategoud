package org.example;

import org.example.Day4AssignmentHelper.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Day 5 Assignment: Java 8 Streams
 */
public final class Day5Assignment {

    private Day5Assignment() { /* utility class */ }

    public static void main(String[] args) {
        List<Employee> employees = buildSampleEmployees();

        // Q1. Employees joined in 2023 (simulate by dateOfBirth year for demo)
        System.out.println("---- Q1: Employees joined in 2023 ----");
        employees.stream()
                .filter(e -> e.getDateOfBirth().getYear() == 2023)
                .map(Employee::getFirstName)
                .forEach(System.out::println);

        // Q2. Salary stats of employees in a department
        String dept = "IT";
        System.out.println("---- Q2: Salary stats for department: " + dept + " ----");
        DoubleSummaryStatistics stats = employees.stream()
                .filter(e -> e.getDept().equalsIgnoreCase(dept))
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        // use your Consumer to print stats
        new DoubleSummaryStatisticsPrinter().accept(stats);


        // Q3. Sorted list of employees by firstName excluding HR
        System.out.println("---- Q3: Employees sorted by firstName excluding HR ----");
        employees.stream()
                .filter(e -> !e.getDept().equalsIgnoreCase("HR"))
                .sorted(Comparator.comparing(Employee::getFirstName))
                .forEach(System.out::println);

        // Q4. Increment salary of employees in IT by 10%
        System.out.println("---- Q4: Employees in IT after 10% increment ----");
        employees.stream()
                .filter(e -> e.getDept().equalsIgnoreCase("IT"))
                .map(e -> new Employee(
                        e.getId(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDateOfBirth(),
                        e.getSalary() * 1.10,
                        e.getDept()
                ))
                .forEach(System.out::println);

        // Q5. Print 50 odd numbers after 100
        System.out.println("---- Q5: First 50 odd numbers after 100 ----");
        IntStream.iterate(101, n -> n + 2)
                .limit(50)
                .forEach(System.out::println);

        // Q6. Comma separated first names ordered by DOB
        System.out.println("---- Q6: Comma separated first names ordered by DOB ----");
        String namesCsv = employees.stream()
                .sorted(Comparator.comparing(Employee::getDateOfBirth))
                .map(Employee::getFirstName)
                .collect(Collectors.joining(", "));
        System.out.println(namesCsv);
    }

    private static List<Employee> buildSampleEmployees() {
        return Arrays.asList(
                new Employee(1, "John", "Doe", LocalDate.of(1990, Month.JANUARY, 10), 1500, "IT"),
                new Employee(2, "Jane", "Smith", LocalDate.of(1985, Month.JUNE, 15), 3000, "HR"),
                new Employee(3, "Mike", "Brown", LocalDate.of(2023, Month.MARCH, 20), 2500, "Finance"),
                new Employee(4, "Emily", "Davis", LocalDate.of(2023, Month.DECEMBER, 5), 1800, "IT"),
                new Employee(5, "Chris", "Taylor", LocalDate.of(1992, Month.APRIL, 12), 2200, "Sales")
        );
    }

    /**
     * Helper consumer to print summary statistics of salaries
     */
    private static class DoubleSummaryStatisticsPrinter implements java.util.function.Consumer<DoubleSummaryStatistics> {
        @Override
        public void accept(DoubleSummaryStatistics stats) {
            System.out.println("Count   : " + stats.getCount());
            System.out.println("Min     : " + stats.getMin());
            System.out.println("Max     : " + stats.getMax());
            System.out.println("Sum     : " + stats.getSum());
            System.out.println("Average : " + stats.getAverage());
        }
    }
}
