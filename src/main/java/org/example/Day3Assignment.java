package org.example;

import org.example.Day4AssignmentHelper.Employee;

import java.util.Optional;

/**
 * Day 3 Assignment: Java 8 Optional
 */
public final class Day3Assignment {

    private Day3Assignment() { /* utility class */ }

    public static void main(String[] args) {
        System.out.println("---- Q1: Safely fetch employee’s department ----");
        q1_safeEmployeeDept();

        System.out.println("\n---- Q2: Print email only if available ----");
        q2_optionalEmail();

        System.out.println("\n---- Q3: Find username length safely ----");
        q3_usernameLength();

        System.out.println("\n---- Q4: Nested object access with flatMap ----");
        q4_nestedOptional();

        System.out.println("\n---- Q5: Validate salary with filter ----");
        q5_salaryValidation();
    }

    /**
     * Q1: Safely fetch employee's department using Optional
     */
    private static void q1_safeEmployeeDept() {
        Employee empWithNullDept = new Employee(1, "John", "Doe", null, 2500, null);

        String dept = Optional.ofNullable(empWithNullDept.getDept())
                .orElse("Unknown");

        System.out.println("Department: " + dept);
    }

    /**
     * Q2: Print email only if available
     */
    private static void q2_optionalEmail() {
        Optional<String> email = Optional.ofNullable(null);

        email.ifPresent(e -> System.out.println("Email: " + e));
        System.out.println("Email present? " + email.isPresent());
    }

    /**
     * Q3: Find username length safely
     */
    private static void q3_usernameLength() {
        Optional<String> username = Optional.ofNullable("rajashekhar");

        int length = username.map(String::length)
                .orElse(0);

        System.out.println("Username length: " + length);
    }

    /**
     * Q4: Nested object access (flatMap)
     */
    private static void q4_nestedOptional() {
        class Address {
            private final String city;
            Address(String city) { this.city = city; }
            public String getCity() { return city; }
        }

        class User {
            private final Address address;
            User(Address address) { this.address = address; }
            public Optional<Address> getAddress() { return Optional.ofNullable(address); }
        }

        User user = new User(new Address("Bangalore"));

        String city = Optional.of(user)
                .flatMap(User::getAddress)
                .map(Address::getCity)
                .orElse("City Not Found");

        System.out.println("User City: " + city);
    }

    /**
     * Q5: Validate salary with filter
     */
    private static void q5_salaryValidation() {
        Optional<Double> salary = Optional.of(2500.0);

        salary.filter(s -> s > 2000)
                .ifPresent(s -> System.out.println("Valid salary: " + s));
    }
}
