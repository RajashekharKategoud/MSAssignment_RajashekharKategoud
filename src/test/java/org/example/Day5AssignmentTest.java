package org.example;

import org.example.Day4AssignmentHelper.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day5Assignment Test - 100% Code Coverage")
class Day5AssignmentTest {

    @Test
    @DisplayName("Should execute main method and cover all stream operations")
    void testMainMethodExecution() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Day5Assignment.main(new String[]{});

            // Convert output to lowercase to avoid case-sensitive comparison
            String output = outputStream.toString().toLowerCase();

            // System.out.println(output); // Uncomment for debugging

            assertAll(
                    () -> assertTrue(output.contains("q1"), "Missing Q1 output"),
                    () -> assertTrue(output.contains("q2"), "Missing Q2 output"),
                    () -> assertTrue(output.contains("q3"), "Missing Q3 output"),
                    () -> assertTrue(output.contains("q4"), "Missing Q4 output"),
                    () -> assertTrue(output.contains("q5"), "Missing Q5 output"),
                    () -> assertTrue(output.contains("q6"), "Missing Q6 output"),
                    () -> assertTrue(output.contains("john"), "Missing John"),
                    () -> assertTrue(output.contains("emily"), "Missing Emily"),
                    () -> assertTrue(output.contains("mike"), "Missing Mike (Finance employee)"),
                    () -> assertTrue(output.contains("it"), "Missing IT department output")
            );
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Should correctly print DoubleSummaryStatistics using private inner consumer")
    void testDoubleSummaryStatisticsPrinter() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(100);
        stats.accept(200);
        stats.accept(300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Object printer = createPrivateInnerPrinter();
        Method acceptMethod = printer.getClass().getDeclaredMethod("accept", DoubleSummaryStatistics.class);
        acceptMethod.setAccessible(true);
        acceptMethod.invoke(printer, stats);

        String output = outputStream.toString();
        assertAll(
                () -> assertTrue(output.contains("Count")),
                () -> assertTrue(output.contains("Min")),
                () -> assertTrue(output.contains("Max")),
                () -> assertTrue(output.contains("Sum")),
                () -> assertTrue(output.contains("Average"))
        );
    }

    @Test
    @DisplayName("Should verify Employee list and transformations manually")
    void testEmployeeFilteringAndSortingLogic() throws Exception {
        Method method = Day5Assignment.class.getDeclaredMethod("buildSampleEmployees");
        method.setAccessible(true);
        List<Employee> employees = (List<Employee>) method.invoke(null);

        assertEquals(5, employees.size());
        assertTrue(employees.stream().anyMatch(e -> e.getDept().equalsIgnoreCase("IT")));
        assertTrue(employees.stream().anyMatch(e -> e.getDateOfBirth().getYear() == 2023));
    }

    @Test
    @DisplayName("Should cover private constructor using reflection")
    void testPrivateConstructor() throws Exception {
        Constructor<Day5Assignment> constructor = Day5Assignment.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Day5Assignment instance = constructor.newInstance();
        assertNotNull(instance);
    }

    private Object createPrivateInnerPrinter() {
        try {
            Class<?> printerClass = Class.forName("org.example.Day5Assignment$DoubleSummaryStatisticsPrinter");
            Constructor<?> constructor = printerClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate private inner printer", e);
        }
    }
}
