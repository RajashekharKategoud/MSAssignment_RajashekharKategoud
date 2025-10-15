package org.example.day10;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

final class BirdTest {

    private static final class TestBird extends Bird {
        TestBird(String name) {
            super(name);
        }

        @Override
        public void makeSound() {
            System.out.println(getName() + " makes a test sound.");
        }
    }

    @Test
    void shouldReturnNameCorrectly() {
        Bird bird = new TestBird("TestBird");
        assertEquals("TestBird", bird.getName());
    }

    @Test
    void shouldPrintEatingMessage() {
        Bird bird = new TestBird("TestBird");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        bird.eat();

        String output = out.toString().trim();
        assertTrue(output.contains("TestBird is eating."));
    }

    @Test
    void shouldPrintSoundMessage() {
        Bird bird = new TestBird("TestBird");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        bird.makeSound();

        String output = out.toString().trim();
        assertTrue(output.contains("TestBird makes a test sound."));
    }
}
