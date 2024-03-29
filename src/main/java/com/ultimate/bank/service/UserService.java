package com.ultimate.bank.service;

import com.ultimate.bank.domain.User;
import com.ultimate.bank.exception.CPFIsNotUniqueException;
import com.ultimate.bank.exception.EmailIsNotUniqueException;
import com.ultimate.bank.exception.UserNotFoundException;
import com.ultimate.bank.model.user.CreateUserRequest;
import com.ultimate.bank.model.user.CreateUserResponse;
import com.ultimate.bank.model.user.UpdateUserRequest;
import com.ultimate.bank.model.user.UpdateUserResponse;
import com.ultimate.bank.repository.UserRepository;
import com.ultimate.bank.util.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {

        String hashedCPF = HashUtil.hashCPF(request.CPF());

        if (userRepository.findByCPF(hashedCPF).isPresent()) {
            throw new CPFIsNotUniqueException("CPF already exists.");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailIsNotUniqueException("Email already exists.");
        }


        User newUser = new User();
        newUser.createUser(request, passwordEncoder, hashedCPF);

        userRepository.save(newUser);

        return ResponseEntity.ok(new CreateUserResponse(
                newUser.getId(),
                newUser.getCPF(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getPassword()));
    }

    @Transactional
    public ResponseEntity<UpdateUserResponse> updateUser(Long id,
                                                         UpdateUserRequest request,
                                                         JwtAuthenticationToken token) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if (user.getCPF().equals(token.getName())) {
            if (request.CPF() != null) {
                String hashedCPF = HashUtil.hashCPF(request.CPF());
                if (!hashedCPF.equals(user.getCPF()) &&
                        userRepository.findByCPF(hashedCPF).isPresent()) {
                    throw new CPFIsNotUniqueException("CPF already exists.");
                }
                user.setCPF(hashedCPF);
            }

            if (request.name() != null) {
                user.setName(request.name());
            }

            if (request.email() != null) {
                if (!request.email().equals(user.getEmail()) &&
                        userRepository.findByEmail(request.email()).isPresent()) {
                    throw new EmailIsNotUniqueException("Email already exists.");
                }
                user.setEmail(request.email());
            }

            if (request.password() != null) {
                user.setPassword(passwordEncoder.encode(request.password()));
            }

            userRepository.save(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new UpdateUserResponse(user));
    }

}
