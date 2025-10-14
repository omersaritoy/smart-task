package com.smart_task.dto;


import com.smart_task.model.User;

public record UserRequest(
        String firstName,
        String lastName,
        String username,
        String email,
        String password
) {
    public static User toUser(UserRequest entity) {
        return  User.builder()
                .firstName(entity.firstName())
                .lastName(entity.lastName())
                .username(entity.username())
                .email(entity.email())
                .password(entity.password())
                .build();

    }
}

