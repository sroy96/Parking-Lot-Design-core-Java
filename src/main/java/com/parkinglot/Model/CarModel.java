package com.parkinglot.Model;

public class CarModel implements Vehicle {
    private String registrationNumber;
    private String color;

    public CarModel(String registrationNumber, String color) {



        if(registrationNumber == null || color == null) {
            throw new IllegalArgumentException("Missing Data regarding Registration Number  && Color");
        }
        else {
            this.registrationNumber = registrationNumber;
            this.color = color;
        }
    }
    public String getColor() {
        return this.color;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }
}
