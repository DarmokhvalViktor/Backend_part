package com.darmokhval.Backend_part.model.entity;

public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    ERole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
