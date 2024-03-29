package com.ultimate.bank.model.user;

import com.ultimate.bank.domain.User;

public record UserResponse(Long id, String name, String email) {
    public UserResponse(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
