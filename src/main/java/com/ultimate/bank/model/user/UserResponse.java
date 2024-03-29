package com.ultimate.bank.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("CPF")
        String CPF,

        String name,
        String email,
        String password) {

}
