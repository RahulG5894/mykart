package com.building.mykart.service;

import com.building.mykart.model.User;
import com.building.mykart.model.UserType;
import com.building.mykart.model.request.AddUserRequest;
import com.building.mykart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean validateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            log.error("User is invalid");
            return false;
        }
        return true;
    }

    public List<User> getAllUserList() {
        return userRepository.findAll();
    }

    public User addUser(AddUserRequest request) {
        User user = createUser(request);
        return userRepository.save(user);
    }

    public User createUser(AddUserRequest request) {
        UserType userType = null;
        if(request.getType() != null) {
            userType = Arrays.stream(UserType.values())
                    .filter(i -> i.name().equalsIgnoreCase(request.getType()))
                    .findFirst().get();
        }
        return User.builder()
                .name(request.getName())
                .username(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .type(userType)
                .build();
    }
}
