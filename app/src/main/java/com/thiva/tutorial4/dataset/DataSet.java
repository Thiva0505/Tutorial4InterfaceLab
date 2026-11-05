package com.thiva.tutorial4.dataset;

// d) DataSet uses Measurable objects, and also implements Measurable
//    (its own measure is the average of the data it holds).
public class DataSet implements Measurable {
    private double sum;
    private Measurable max;
    private int count;
    private String name;

    public DataSet() {
        sum = 0;
        count = 0;
        max = null;
        name = "DataSet";
    }

    public void add(Measurable x) {
        sum = sum + x.getMeasure();
        if (count == 0 || max.getMeasure() < x.getMeasure()) {
            max = x;
        }
        count++;
    }

    public Measurable getMax() {
        return max;
    }

    public double getAverage() {
        if (count == 0) return 0;
        return sum / count;
    }

    // Added for the Android screen: how many items are in the set
    public int getCount() {
        return count;
    }

    @Override
    public double getMeasure() {
        return getAverage();
    }

    @Override
    public String getName() {
        return name;
    }
}
