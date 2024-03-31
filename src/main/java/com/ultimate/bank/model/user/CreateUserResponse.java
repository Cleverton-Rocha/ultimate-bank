package com.ultimate.bank.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ultimate.bank.domain.User;

public record CreateUserResponse(

        @JsonProperty("id")
        Long id,
        @JsonProperty("CPF")
        String CPF,
        String firstName,
        String lastName,
        String email,
        String password) {

    public CreateUserResponse(User user) {
        this(user.getId(), user.getCPF(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

}
