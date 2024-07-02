package com.dac.api.app.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER"),
    ORGANIZER("ORGANIZER");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
