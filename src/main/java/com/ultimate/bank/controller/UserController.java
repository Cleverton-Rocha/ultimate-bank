package com.ultimate.bank.controller;

import com.ultimate.bank.model.user.*;
import com.ultimate.bank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{hashedCPF}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String hashedCPF, JwtAuthenticationToken token) {
        return userService.getUser(hashedCPF, token);
    }

    @PatchMapping("/{CPF}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable String CPF,
                                                         @RequestBody @Valid UpdateUserRequest request,
                                                         JwtAuthenticationToken token) {
        return userService.updateUser(CPF, request, token);
    }

    @DeleteMapping("/{CPF}")
    public ResponseEntity<Void> deleteUser(@PathVariable String CPF, JwtAuthenticationToken token) {
        return userService.deleteUser(CPF, token);
    }
}