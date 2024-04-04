package com.ultimate.bank.service;

import com.ultimate.bank.exception.BadCredentialsException;
import com.ultimate.bank.model.auth.LoginRequest;
import com.ultimate.bank.model.auth.LoginResponse;
import com.ultimate.bank.repository.UserRepository;
import com.ultimate.bank.util.HashUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {

        var hashedCPF = HashUtil.hashCPF(request.CPF());

        var user = userRepository.findByCPF(hashedCPF);

        if (user.isEmpty() || user.get().isLoginIncorrect(request.password(), passwordEncoder)) {
            throw new BadCredentialsException("Invalid CPF or password.");
        }

        var now = Instant.now().atZone(ZoneId.systemDefault());
        var expiresIn = 7200L;

        var claims = JwtClaimsSet.builder()
                                 .issuer("ultimate-bank")
                                 .subject(hashedCPF)
                                 .issuedAt(now.toInstant())
                                 .expiresAt(now.plusSeconds(expiresIn).toInstant()).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(jwtValue, hashedCPF, expiresIn));
    }
}
