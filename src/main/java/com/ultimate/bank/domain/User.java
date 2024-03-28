package com.ultimate.bank.domain;

import com.ultimate.bank.model.auth.LoginRequest;
import com.ultimate.bank.model.user.UserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Table(name = "users")
@Entity
@Data
public class User {
    @Id
    @Column(length = 11, nullable = false, unique = true)
    private String CPF;

    @Column(nullable = false, name = "hashed_CPF")
    private String hashedCPF;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public void createUser(UserRequest request,
                           BCryptPasswordEncoder passwordEncoder,
                           String HashedCPF) {
        this.CPF = passwordEncoder.encode(request.CPF());
        this.hashedCPF = HashedCPF;
        this.name = request.name();
        this.email = request.email();
        this.password = passwordEncoder.encode(request.password());
    }


    public boolean isLoginCorrect(LoginRequest request,
                                  BCryptPasswordEncoder passwordEncoder) {
       return passwordEncoder.matches(request.password(), this.password);
    }

}
