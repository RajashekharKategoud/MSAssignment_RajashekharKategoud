package org.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import org.example.Day4AssignmentHelper.Employee;
import org.example.Day4AssignmentHelper.User;
import org.example.Day4AssignmentHelper.UserNameGenerator;

/**
 * Day 4 Assignment: Functional Interfaces + Lambdas
 */
public final class Day4Assignment {

    private Day4Assignment() { /* utility class */ }

    public static void main(String[] args) {
        List<Employee> employees = buildSampleEmployees();

        // Q1. Predicate + Consumer
        Predicate<Employee> highSalaryPredicate = e -> e.getSalary() > 2000;
        Consumer<Employee> employeePrinter = e ->
                System.out.println("Employee: " + e.getFirstName() + " " + e.getLastName()
                        + ", Salary=" + e.getSalary()
                        + ", Dept=" + e.getDept());

        System.out.println("---- Employees with salary > 2000 ----");
        employees.stream()
                .filter(highSalaryPredicate)
                .forEach(employeePrinter);

        // Q2. Function + Supplier
        Supplier<String> passwordSupplier = Day4Assignment::generateRandomPassword;
        Function<Employee, User> employeeToUser = e -> new User(
                e.getId(),
                e.getFirstName() + e.getLastName() + e.getDateOfBirth().getYear() + e.getId(),
                passwordSupplier.get()
        );

        List<User> users = employees.stream()
                .map(employeeToUser)
                .collect(Collectors.toList());

        System.out.println("---- Users created from employees ----");
        users.forEach(System.out::println);

        // Lambda: Sort employees by month of birth
        System.out.println("---- Employees sorted by month of birth ----");
        employees.stream()
                .sorted(Comparator.comparing(e -> e.getDateOfBirth().getMonthValue()))
                .forEach(employeePrinter);

        // Lambda: Two threads
        Thread employeeThread = new Thread(() -> {
            System.out.println("---- Thread printing employees ----");
            employees.forEach(employeePrinter);
        });

        Thread userThread = new Thread(() -> {
            System.out.println("---- Thread printing users ----");
            users.forEach(System.out::println);
        });

        employeeThread.start();
        userThread.start();

        try {
            employeeThread.join();
            userThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Custom functional interface
        UserNameGenerator generator = (first, last, year, id) -> first + last + year + id;
        User customUser = new User(
                999,
                generator.generate("Alice", "Wonder", 1995, 999),
                passwordSupplier.get()
        );

        System.out.println("---- User created using custom UserNameGenerator ----");
        System.out.println(customUser);
    }

    private static List<Employee> buildSampleEmployees() {
        return Arrays.asList(
                new Employee(1, "John", "Doe", LocalDate.of(1990, Month.JANUARY, 10), 1500, "IT"),
                new Employee(2, "Jane", "Smith", LocalDate.of(1985, Month.JUNE, 15), 3000, "HR"),
                new Employee(3, "Mike", "Brown", LocalDate.of(1992, Month.MARCH, 20), 2500, "Finance"),
                new Employee(4, "Emily", "Davis", LocalDate.of(1988, Month.DECEMBER, 5), 1800, "Sales")
        );
    }

    private static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
