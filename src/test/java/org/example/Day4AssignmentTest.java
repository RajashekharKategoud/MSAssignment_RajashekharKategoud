package org.example;

import org.example.Day4AssignmentHelper.Employee;
import org.example.Day4AssignmentHelper.User;
import org.example.Day4AssignmentHelper.UserNameGenerator;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class Day4AssignmentTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should execute main() fully without exceptions")
    void testMainExecution() {
        assertDoesNotThrow(() -> Day4Assignment.main(new String[]{}));

        String output = outputStream.toString();

        // Validate all expected sections printed
        assertTrue(output.contains("Employees with salary > 2000"));
        assertTrue(output.contains("Users created from employees"));
        assertTrue(output.contains("Employees sorted by month of birth"));
        assertTrue(output.contains("Thread printing employees"));
        assertTrue(output.contains("Thread printing users"));
        assertTrue(output.contains("User created using custom UserNameGenerator"));
    }

    @Test
    @DisplayName("Should build sample employees correctly using reflection")
    void testBuildSampleEmployees() throws Exception {
        Method method = Day4Assignment.class.getDeclaredMethod("buildSampleEmployees");
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Employee> employees = (List<Employee>) method.invoke(null);

        assertEquals(4, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("Sales", employees.get(3).getDept());
    }

    @Test
    @DisplayName("Should generate random password of correct length and randomness")
    void testGenerateRandomPasswordViaReflection() throws Exception {
        Method method = Day4Assignment.class.getDeclaredMethod("generateRandomPassword");
        method.setAccessible(true);

        String password1 = (String) method.invoke(null);
        String password2 = (String) method.invoke(null);

        assertEquals(16, password1.length());
        assertEquals(16, password2.length());
        assertNotEquals(password1, password2, "Passwords should be random");
    }

    @Test
    @DisplayName("Should validate custom UserNameGenerator functional interface")
    void testUserNameGenerator() {
        UserNameGenerator generator = (first, last, year, id) -> first + last + year + id;
        String result = generator.generate("Alice", "Wonder", 1995, 999);
        assertEquals("AliceWonder1995999", result);
    }

    @Test
    @DisplayName("Should verify Employee and User objects behave correctly")
    void testEmployeeAndUserObjects() {
        Employee emp = new Employee(1, "John", "Doe", LocalDate.of(1990, Month.JANUARY, 10), 2500, "IT");
        User user = new User(1, "JohnDoe19901", "Abc123");

        assertEquals(1, emp.getId());
        assertEquals("John", emp.getFirstName());
        assertEquals("Doe", emp.getLastName());
        assertEquals("IT", emp.getDept());
        assertTrue(emp.getSalary() > 2000);

        assertEquals("JohnDoe19901", user.getUserName());
        assertTrue(user.toString().contains("Abc123"));
    }
}
