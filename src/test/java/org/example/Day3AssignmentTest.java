package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;


class Day3AssignmentTest {

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Q1: Should safely handle null department")
    void testQ1SafeEmployeeDept() throws Exception {
        invokePrivateMethod("q1_safeEmployeeDept");
        String result = output.toString();
        assertTrue(result.contains("Department: Unknown"));
    }

    @Test
    @DisplayName("Q2: Should handle empty optional email")
    void testQ2OptionalEmail() throws Exception {
        invokePrivateMethod("q2_optionalEmail");
        String result = output.toString();
        assertTrue(result.contains("Email present? false"));
    }

    @Test
    @DisplayName("Q3: Should safely find username length")
    void testQ3UsernameLength() throws Exception {
        invokePrivateMethod("q3_usernameLength");
        String result = output.toString();
        assertTrue(result.contains("Username length: 11"));
    }

    @Test
    @DisplayName("Q4: Should extract city via flatMap")
    void testQ4NestedOptional() throws Exception {
        invokePrivateMethod("q4_nestedOptional");
        String result = output.toString();
        assertTrue(result.contains("User City: Bangalore"));
    }

    @Test
    @DisplayName("Q5: Should validate salary correctly")
    void testQ5SalaryValidation() throws Exception {
        invokePrivateMethod("q5_salaryValidation");
        String result = output.toString();
        assertTrue(result.contains("Valid salary: 2500.0"));
    }

    @Test
    @DisplayName("Main method should run all questions without error")
    void testMainExecution() {
        assertDoesNotThrow(() -> Day3Assignment.main(new String[0]));
    }


    private void invokePrivateMethod(String methodName) throws Exception {
        Method method = Day3Assignment.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(null);
    }
}
