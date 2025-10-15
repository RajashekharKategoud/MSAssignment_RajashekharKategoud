package org.example.day10;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class BirdSimulatorTest {

    @Test
    void shouldSimulateAllBirdsCorrectly() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BirdSimulator.main(new String[]{});

        String output = out.toString();

        // Verify output for each bird’s actions
        assertTrue(output.contains("Crow is eating."));
        assertTrue(output.contains("Crow says: Caw Caw!"));
        assertTrue(output.contains("Crow is flying high."));

        assertTrue(output.contains("Parrot is eating."));
        assertTrue(output.contains("Parrot says: Hello!"));
        assertTrue(output.contains("Parrot is flying beautifully."));

        assertTrue(output.contains("Penguin is eating."));
        assertTrue(output.contains("Penguin says: Honk Honk!"));
        assertTrue(output.contains("Penguin is swimming."));
    }

    @Test
    void shouldCoverPrivateConstructorUsingReflection() throws Exception {
        java.lang.reflect.Constructor<BirdSimulator> constructor = BirdSimulator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
