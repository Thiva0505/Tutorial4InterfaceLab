package com.thiva.tutorial4.mobile;

// b) MobileDevice implements RadioTuner
public class MobileDevice implements RadioTuner {
    protected String manufacturer;
    protected String model;
    public String station;
    public double frequency;

    public MobileDevice(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    // Android cannot show System.out on screen, so the details are
    // built as a String. printDetails() still prints them (to Logcat).
    public String getDetails() {
        return "Manufacturer: " + manufacturer + "\nModel: " + model;
    }

    public void printDetails() {
        System.out.println(getDetails());
    }

    @Override
    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
