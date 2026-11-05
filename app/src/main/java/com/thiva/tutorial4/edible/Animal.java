package com.thiva.tutorial4.edible;

// g) Animal implements Edible
public class Animal implements Edible {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " needs to be cooked first before eating.";
    }
}
