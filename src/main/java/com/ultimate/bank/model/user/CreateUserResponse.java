package com.ultimate.bank.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserResponse(

        @JsonProperty("id")
        Long id,
        @JsonProperty("CPF")
        String CPF,
        String name,
        String email,
        String password) {

}
