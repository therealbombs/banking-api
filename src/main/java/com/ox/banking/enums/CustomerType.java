package com.ox.banking.enums;

public enum CustomerType {
    RETAIL("Retail"),
    PRIVATE("Private Banking"),
    CORPORATE("Corporate"),
    PREMIUM("Premium");

    private final String description;
    
    CustomerType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}