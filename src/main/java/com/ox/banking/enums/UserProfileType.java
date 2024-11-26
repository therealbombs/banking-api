package com.ox.banking.enums;
public enum UserProfileType {
    RETAIL("Retail User"),
    PRIVATE("Private Banking"),
    CORPORATE("Corporate"),
    ADMIN("Administrator");

    private final String description;

    UserProfileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}