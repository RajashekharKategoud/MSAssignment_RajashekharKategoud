package org.example.day10;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class CrowTest {

    @Test
    void shouldMakeSoundCorrectly() {
        Crow crow = new Crow();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        crow.makeSound();

        assertTrue(out.toString().contains("Crow says: Caw Caw!"));
    }

    @Test
    void shouldFlyCorrectly() {
        Crow crow = new Crow();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        crow.fly();

        assertTrue(out.toString().contains("Crow is flying high."));
    }
}
