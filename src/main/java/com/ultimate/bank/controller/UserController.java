package com.ultimate.bank.controller;


import com.ultimate.bank.model.user.UserRequest;
import com.ultimate.bank.model.user.UserResponse;
import com.ultimate.bank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}
