package com.thiva.tutorial4.mobile;

// c) SmartPhone, subclass of MobileDevice
public class SmartPhone extends MobileDevice {
    private String type;

    public SmartPhone(String manufacturer, String model, String type) {
        super(manufacturer, model);
        this.type = type;
    }

    @Override
    public String getDetails() {
        return "SmartPhone Detail:\n" + super.getDetails() + "\nType: " + type;
    }
}
