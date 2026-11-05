package com.thiva.tutorial4.mobile;

// d) BasicPhone, subclass of MobileDevice
public class BasicPhone extends MobileDevice {
    public BasicPhone(String manufacturer, String model) {
        super(manufacturer, model);
    }

    @Override
    public String getDetails() {
        return "Basic Phone Detail:\n" + super.getDetails();
    }
}
