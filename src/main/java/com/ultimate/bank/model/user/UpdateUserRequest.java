package com.ultimate.bank.model.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequest(

        @Size(min = 1, message = "First name must have at least 1 character.")
        String firstName,
        @Size(min = 1, message = "Last name must have at least 1 character.")
        String lastName,
        @Email(message = "Write a valid email.")
        String email) {
}
