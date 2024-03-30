package com.ultimate.bank.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ultimate.bank.domain.User;

public record CreateUserResponse(

        @JsonProperty("id") Long id, @JsonProperty("CPF") String CPF, String name, String email, String password) {

    public CreateUserResponse(User user) {
        this(user.getId(), user.getCPF(), user.getName(), user.getEmail(), user.getPassword());
    }

}
