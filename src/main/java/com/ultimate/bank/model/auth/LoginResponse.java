package com.ultimate.bank.model.auth;

public record LoginResponse(String accessToken, String hashedCPF, Long expiresIn) {
}
