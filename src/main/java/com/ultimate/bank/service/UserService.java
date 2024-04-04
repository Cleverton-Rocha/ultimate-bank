package com.ultimate.bank.service;

import com.ultimate.bank.domain.Account;
import com.ultimate.bank.domain.User;
import com.ultimate.bank.exception.BadCredentialsException;
import com.ultimate.bank.exception.IsNotUniqueException;
import com.ultimate.bank.exception.NotFoundException;
import com.ultimate.bank.model.user.*;
import com.ultimate.bank.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AccountRepository accountRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {

        String hashedCPF = HashUtil.hashCPF(request.CPF());

        if (userRepository.findByCPF(hashedCPF).isPresent()) {
            throw new IsNotUniqueException("CPF already exists.");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IsNotUniqueException("Email already exists.");
        }


        User newUser = new User();
        newUser.createUser(request, passwordEncoder, hashedCPF);

        Account newAccount = new Account();
        newAccount.createAccount(newUser);

        userRepository.save(newUser);
        accountRepository.save(newAccount);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUserResponse(newUser));
    }

    @Transactional
    public ResponseEntity<UserResponse> getUser(String hashedCPF, JwtAuthenticationToken token) {

        User user = userRepository.findByCPF(hashedCPF).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getCPF().equals(token.getName())) {
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Transactional
    public ResponseEntity<UpdateUserResponse> updateUser(String hashedCPF, UpdateUserRequest request,
                                                         JwtAuthenticationToken token) {
        User user = userRepository.findByCPF(hashedCPF).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getCPF().equals(token.getName())) {

            if (request.firstName() != null) {
                user.setFirstName(request.firstName());
            }

            if (request.lastName() != null) {
                user.setLastName(request.lastName());
            }

            if (request.email() != null) {
                if (!request.email().equals(user.getEmail()) && userRepository.findByEmail(request.email())
                                                                              .isPresent()) {
                    throw new IsNotUniqueException("Email already exists.");
                }
                user.setEmail(request.email());
            }

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new UpdateUserResponse(user));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Transactional
    public ResponseEntity<Void> updatePassword(String hashedCPF, UpdatePasswordRequest request,
                                               JwtAuthenticationToken token) {

        User user = userRepository.findByCPF(hashedCPF).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getCPF().equals(token.getName())) {

            if (user.isLoginIncorrect(request.actualPassword(), passwordEncoder)){
                throw new BadCredentialsException("Invalid password.");
            }

            user.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Transactional
    public ResponseEntity<Void> deleteUser(String hashedCPF, JwtAuthenticationToken token) {

        User user = userRepository.findByCPF(hashedCPF).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getCPF().equals(token.getName())) {
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
