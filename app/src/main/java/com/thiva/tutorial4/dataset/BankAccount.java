package com.thiva.tutorial4.dataset;

// b) BankAccount implements Measurable
public class BankAccount implements Measurable {
    private int accNumber;
    private double balance;
    private static int lastAssignedNumber = 1000;

    public BankAccount() {
        this(0);
    }

    public BankAccount(double balance) {
        lastAssignedNumber++;
        this.accNumber = lastAssignedNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accNumber;
    }

    @Override
    public double getMeasure() {
        return balance;
    }

    @Override
    public String getName() {
        return "Account " + accNumber;
    }
}
