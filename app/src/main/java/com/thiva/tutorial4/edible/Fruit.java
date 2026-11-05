package com.thiva.tutorial4.edible;

// h) Fruit implements Edible (abstract: each fruit supplies its own howToEat())
public abstract class Fruit implements Edible {
    protected String name;

    public Fruit(String name) {
        this.name = name;
    }
}
