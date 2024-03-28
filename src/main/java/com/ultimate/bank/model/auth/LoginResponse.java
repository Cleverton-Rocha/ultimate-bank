package com.ultimate.bank.model.auth;

public record LoginResponse(String accessToken, Long expiresIn) {
}
