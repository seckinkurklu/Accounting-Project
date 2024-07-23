package com.cydeo.enums;

public enum InvoiceStatus {
    AWAITING_APPROVAL("Awaiting Approval") ,APROVED("Aproved");
    private final String value;

    InvoiceStatus(String value) {
        this.value = value;
    }
    public String getValue(){
        return  value;
    }
}
