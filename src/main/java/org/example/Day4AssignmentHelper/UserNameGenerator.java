package org.example.Day4AssignmentHelper;

@FunctionalInterface
public interface UserNameGenerator {
    String generate(String firstName, String lastName, int yearOfBirth, int id);
}
