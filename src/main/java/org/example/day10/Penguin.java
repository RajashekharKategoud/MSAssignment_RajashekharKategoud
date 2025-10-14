package org.example.day10;


public final class Penguin extends Bird {

    public Penguin() {
        super("Penguin");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Honk Honk!");
    }

    public void swim() {
        System.out.println(getName() + " is swimming.");
    }
}
