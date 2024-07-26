package com.cydeo.enums;

public enum CompanyStatus {

    ACTIVE("Active"),PASSIVE("Passive");

    private final String value;

    public String getValue() {
        return value;
    }

    CompanyStatus(String value) {
        this.value = value;
    }
}
