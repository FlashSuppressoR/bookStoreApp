package com.flashsuppressor.java.lab.entity;

public enum Permission {
    CUSTOMER_READ("permission:reed"),
    CUSTOMER_WRITE("permission:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
