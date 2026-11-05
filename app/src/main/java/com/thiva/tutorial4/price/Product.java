package com.thiva.tutorial4.price;

// b) Product implements Price
// getPrice() keeps the tutorial's void signature; the message is also
// stored so the Android screen can display it.
public class Product implements Price {
    private String type;
    private Customer buy;
    private String result = "";
    private boolean canBuy;

    public Product(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "The product is ";
    }

    // Customer buys the product if salary exceeds RM4000
    @Override
    public void getPrice(Customer c) {
        buy = c;
        canBuy = buy.getSalary() > 4000;
        if (canBuy) {
            result = "The customer's name is " + buy.getName() + " with salary RM" + buy.getSalary()
                    + " can buy this product.\nYour salary is higher than the price's product.";
        } else {
            result = "The customer's name is " + buy.getName() + " with salary RM" + buy.getSalary()
                    + " cannot buy this product.\nThe product price is more than your salary.";
        }
        System.out.println(toString());
        System.out.println(result);
    }

    public String getResult() {
        return result;
    }

    public boolean isCanBuy() {
        return canBuy;
    }
}
