package com.smart_task.dto;


import com.smart_task.model.Role;
import com.smart_task.model.User;

import java.util.Date;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        Role role,
        Date createdAt,
        Date updatedAt
) {
    public static UserResponse toUserResponse(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

