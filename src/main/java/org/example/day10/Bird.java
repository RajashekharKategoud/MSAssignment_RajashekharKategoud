package org.example.day10;


public abstract class Bird {

    private final String name;

    protected Bird(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public abstract void makeSound();
}

