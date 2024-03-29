package com.ultimate.bank.service;

import com.google.common.hash.Hashing;
import com.ultimate.bank.domain.User;
import com.ultimate.bank.exception.CPFIsNotUniqueException;
import com.ultimate.bank.exception.EmailIsNotUniqueException;
import com.ultimate.bank.model.user.UserRequest;
import com.ultimate.bank.model.user.UserResponse;
import com.ultimate.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<UserResponse> createUser(UserRequest request) {

        String hashedCPF = hashCPF(request.CPF());

        if (userRepository.findByCPF(hashedCPF).isPresent()) {
            throw new CPFIsNotUniqueException("CPF already exists.");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailIsNotUniqueException("Email already exists.");
        }


        User newUser = new User();
        newUser.createUser(request, passwordEncoder, hashedCPF);

        userRepository.save(newUser);

        return ResponseEntity.ok(new UserResponse(
                newUser.getCPF(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getPassword()));
    }

    private String hashCPF(String CPF) {
        return Hashing.sha256()
                .hashString(CPF, StandardCharsets.UTF_8)
                .toString();
    }
}
