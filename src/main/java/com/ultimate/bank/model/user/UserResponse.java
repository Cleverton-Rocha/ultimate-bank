package com.ultimate.bank.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("CPF")
        String CPF,

        @JsonProperty("hashed_CPF")
        String hashedCPF,
        String name,
        String email,
        String password){
}
