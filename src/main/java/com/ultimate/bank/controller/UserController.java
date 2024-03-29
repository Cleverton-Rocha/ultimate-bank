package com.ultimate.bank.controller;


import com.ultimate.bank.model.user.CreateUserRequest;
import com.ultimate.bank.model.user.CreateUserResponse;
import com.ultimate.bank.model.user.UpdateUserRequest;
import com.ultimate.bank.model.user.UpdateUserResponse;
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

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id,
                                                         @RequestBody @Valid
                                                         UpdateUserRequest request,
                                                         JwtAuthenticationToken token) {
        return userService.updateUser(id, request, token);
    }

}
