package org.example.day10;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class PenguinTest {

    @Test
    void shouldMakeSoundCorrectly() {
        Penguin penguin = new Penguin();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        penguin.makeSound();

        assertTrue(out.toString().contains("Penguin says: Honk Honk!"));
    }

    @Test
    void shouldSwimCorrectly() {
        Penguin penguin = new Penguin();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        penguin.swim();

        assertTrue(out.toString().contains("Penguin is swimming."));
    }
}
