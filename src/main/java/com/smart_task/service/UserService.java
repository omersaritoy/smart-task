package com.smart_task.service;


import com.smart_task.dto.UserRequest;
import com.smart_task.dto.UserResponse;
import com.smart_task.exception.UserNotFoundException;
import com.smart_task.model.User;
import com.smart_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceRepository<UserRequest, UserResponse> {

    private final UserRepository userRepository;


    @Override
    public UserResponse register(UserRequest entity) {
        User savedUser = userRepository.save(UserRequest.toUser(entity));
        return UserResponse.toUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponse::toUserResponse).toList();
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found By Id: " + id));
        return UserResponse.toUserResponse(user);
    }
}
