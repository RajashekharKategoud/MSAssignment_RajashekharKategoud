package org.example.day10;

import org.example.day10.*;

import java.util.Arrays;
import java.util.List;

public final class BirdSimulator {

    private BirdSimulator() { /* Utility class */ }

    public static void main(String[] args) {
        List<Bird> birds = Arrays.asList(
                new Crow(),
                new Parrot(),
                new Penguin()
        );

        for (Bird bird : birds) {
            bird.eat();
            bird.makeSound();

            if (bird instanceof Flyable) {
                ((Flyable) bird).fly();
            } else if (bird instanceof Penguin) {
                ((Penguin) bird).swim();
            }

            System.out.println();
        }
    }
}
