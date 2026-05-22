package com.building.mykart.controller;

import com.building.mykart.model.User;
import com.building.mykart.model.request.AddUserRequest;
import com.building.mykart.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "Operations related to Users")
public class UserController {

    private UserService userService;

    @GetMapping
    @Operation(summary = "Get All the Users", description = "Get all the Users")
    public List<User> getAllUsers() {
        return userService.getAllUserList();
    }

    @PostMapping
    @Operation(summary = "Add a new User", description = "Add a new User")
    public User addUser(@RequestBody AddUserRequest request) {
        return userService.addUser(request);
    }
}
