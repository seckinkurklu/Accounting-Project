package com.cydeo.enums;

public enum ProductUnit {

    LBS ("Libre"), GALLON ("Gallon"), PCS("Pieces"), KG("Kilogram"), METER("Meter"), INCH("Inch"), FEET("Feet");

    private final String values;

    ProductUnit(String values) {
        this.values = values;
    }

}
