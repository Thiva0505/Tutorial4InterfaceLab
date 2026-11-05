package com.thiva.tutorial4.price;

// e) Car implements Price
public class Car implements Price {
    private String brand;
    private double priceCar;
    private Customer buy;
    private String result = "";
    private boolean canBuy;

    public Car(String brand, double priceCar) {
        this.brand = brand;
        this.priceCar = priceCar;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "The car brand is " + brand + " with price RM" + priceCar + ".";
    }

    // Customer buys the car if salary exceeds RM8000
    @Override
    public void getPrice(Customer c) {
        buy = c;
        canBuy = buy.getSalary() > 8000;
        if (canBuy) {
            result = "The customer's name is " + buy.getName() + " with salary RM" + buy.getSalary()
                    + " can buy this car.\nYour salary is higher than the price's car.";
        } else {
            result = "The customer's name is " + buy.getName() + " with salary RM" + buy.getSalary()
                    + " cannot buy this car.\nThe car price is more than your salary.";
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
