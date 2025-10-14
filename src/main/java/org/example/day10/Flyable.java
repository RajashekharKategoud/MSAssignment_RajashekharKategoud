package org.example.day10;

/**
 * Interface segregating flying behavior.
 * Only birds that can fly will implement this.
 * Demonstrates ISP and SRP.
 */
public interface Flyable {
    void fly();
}
