package com.thiva.tutorial4.edible;

// j) Apple, subclass of Fruit
public class Apple extends Fruit {
    public Apple(String name) {
        super(name);
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " can make apple pie.";
    }
}
