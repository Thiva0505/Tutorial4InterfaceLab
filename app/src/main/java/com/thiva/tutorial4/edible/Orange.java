package com.thiva.tutorial4.edible;

// i) Orange, subclass of Fruit
public class Orange extends Fruit {
    public Orange(String name) {
        super(name);
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " can make orange butter cake.";
    }
}
