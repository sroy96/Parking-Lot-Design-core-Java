package com.parkinglot.Model;

import org.junit.Assert;
import org.junit.Test;

public class CarModelTest {
    @Test
    public void validTest() {
        CarModel carModel = new CarModel(
                "KA-01-HH-3141", "White");
        Assert.assertEquals("White", carModel.getColor());
        Assert.assertEquals("KA-01-HH-3141", carModel.getRegistrationNumber());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testWithNullRegistrationNumber() {
        new CarModel(null, "WHITE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullColor() {
        new CarModel("KA-01-HH-3141", null);
    }
}

