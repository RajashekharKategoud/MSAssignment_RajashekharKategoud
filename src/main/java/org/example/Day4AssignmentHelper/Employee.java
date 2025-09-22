package org.example.Day4AssignmentHelper;

import java.time.LocalDate;

/**
 * Employee class
 */
public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final double salary;
    private final String dept;

    public Employee(int id, String firstName, String lastName, LocalDate dateOfBirth, double salary, String dept) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.dept = dept;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public double getSalary() { return salary; }
    public String getDept() { return dept; }
}
