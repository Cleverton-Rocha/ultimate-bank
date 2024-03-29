package com.ultimate.bank.model.user;

import com.ultimate.bank.domain.User;

public record UpdateUserResponse(
        String CPF,
        String name,
        String email,
        String password
) {
    public UpdateUserResponse(User user){
        this(user.getCPF(), user.getName(), user.getEmail(), user.getPassword());
    }
}
