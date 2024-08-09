package com.cydeo.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD("USD"), EUR("EUR");


    private final String value;


    Currency(String value) {
        this.value = value;
    }
}