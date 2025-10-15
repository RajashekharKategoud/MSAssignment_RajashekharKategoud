package org.example.day10;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class ParrotTest {

    @Test
    void shouldMakeSoundCorrectly() {
        Parrot parrot = new Parrot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parrot.makeSound();

        assertTrue(out.toString().contains("Parrot says: Hello!"));
    }

    @Test
    void shouldFlyCorrectly() {
        Parrot parrot = new Parrot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parrot.fly();

        assertTrue(out.toString().contains("Parrot is flying beautifully."));
    }
}
