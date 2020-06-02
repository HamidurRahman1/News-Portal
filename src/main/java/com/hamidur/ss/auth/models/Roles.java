package com.hamidur.ss.auth.models;

public enum Roles
{
    ADMIN("ADMIN"),
    USER("USER"),
    EDITOR("EDITOR"),
    PUBLISHER("PUBLISHER");

    private final String role;

    private Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
