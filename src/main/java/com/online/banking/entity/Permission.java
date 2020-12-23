package com.online.banking.entity;

public enum Permission {
    ALL("ALL");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
