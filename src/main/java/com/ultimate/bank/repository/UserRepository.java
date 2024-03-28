package com.ultimate.bank.repository;

import com.ultimate.bank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCPF(String CPF);

    Optional<User> findByHashedCPF(String hashedCPF);
}