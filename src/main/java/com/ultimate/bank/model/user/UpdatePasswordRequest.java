package com.ultimate.bank.model.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @Size(min = 8, max = 100, message = "Password must have at least 8 characters.") @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                message = "Password must have at least one uppercase letter, one lowercase letter and one number.")
        String actualPassword,

        @Size(min = 8, max = 100, message = "Password must have at least 8 characters.") @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                message = "Password must have at least one uppercase letter, one lowercase letter and one number.")
        String newPassword) {
}
