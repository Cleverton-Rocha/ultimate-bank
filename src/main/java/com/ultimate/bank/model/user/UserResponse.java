package com.ultimate.bank.model.user;

import com.ultimate.bank.domain.Account;
import com.ultimate.bank.domain.User;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Account account) {
    public UserResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAccount());
    }
}