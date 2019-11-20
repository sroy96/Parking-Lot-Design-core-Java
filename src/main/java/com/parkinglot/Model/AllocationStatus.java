package com.parkinglot.Model;

public class AllocationStatus {

    private int slot;
    private String registrationNumber;
    private String color;

    public AllocationStatus() {
    }

    public AllocationStatus(int slot, String registrationNumber, String color) {
        this.slot = slot;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString(){
        return slot + "           " + registrationNumber + "      " + color;
    }
}
