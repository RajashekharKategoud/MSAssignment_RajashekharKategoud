package org.example.day10;


public final class Crow extends Bird implements Flyable {

    public Crow() {
        super("Crow");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Caw Caw!");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " is flying high.");
    }
}
