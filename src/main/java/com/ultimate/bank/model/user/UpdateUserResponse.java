package com.ultimate.bank.model.user;

import com.ultimate.bank.domain.User;

public record UpdateUserResponse(
        String CPF,
        String firstName,
        String lastName,
        String email,
        String password) {
    public UpdateUserResponse(User user) {
        this(user.getCPF(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }
}
