package com.smart_task.service;


import ch.qos.logback.core.status.ErrorStatus;
import com.smart_task.dto.UserRequest;
import com.smart_task.dto.UserResponse;
import com.smart_task.exception.ErrorResponse;
import com.smart_task.exception.GlobalExcepiton;
import com.smart_task.exception.UserAlreadyExist;
import com.smart_task.exception.UserNotFoundException;
import com.smart_task.model.User;
import com.smart_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceRepository<UserRequest, UserResponse> , UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse register(UserRequest entity) {
        if(userRepository.existsByEmail(entity.email())||userRepository.existsByUsername(entity.username())){
            throw new UserAlreadyExist("This user already exists!");
        }
        User user = UserRequest.toUser(entity);
        user.setPassword(passwordEncoder.encode(entity.password()));
        User savedUser = userRepository.save(user);
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

    private User userFindById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userFindById(id);

        Optional.ofNullable(request.firstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(user::setLastName);
        Optional.ofNullable(request.username()).ifPresent(user::setUsername);
        Optional.ofNullable(request.email()).ifPresent(user::setEmail);
        Optional.ofNullable(request.password())
                .ifPresent(password -> user.setPassword(request.password()));

        return UserResponse.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
