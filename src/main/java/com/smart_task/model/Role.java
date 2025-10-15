package com.smart_task.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    USER("User");
    private final String role;

    private Role(String role) {
        this.role = role;
    }

}
