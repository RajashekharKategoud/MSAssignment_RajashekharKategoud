package org.example.day10;


public final class Parrot extends Bird implements Flyable {

    public Parrot() {
        super("Parrot");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Hello!");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " is flying beautifully.");
    }
}
