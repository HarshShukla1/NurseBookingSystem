package com.harsh.nursebookingsystem.auth;

import com.harsh.nursebookingsystem.auth.web.AuthResponse;
import com.harsh.nursebookingsystem.auth.web.LoginRequest;
import com.harsh.nursebookingsystem.auth.web.RegisterRequest;
import com.harsh.nursebookingsystem.user.domain.Role;
import com.harsh.nursebookingsystem.user.domain.User;
import com.harsh.nursebookingsystem.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
@Transactional
public class AuthService {
    private final UserRepository users;
    private final BCryptPasswordEncoder passwords = new BCryptPasswordEncoder();

    public AuthService(UserRepository users) {
        this.users = users;
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.role() != Role.PATIENT && request.role() != Role.NURSE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only patient and nurse accounts can be registered here");
        }
        String email = normaliseEmail(request.email());
        if (users.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwords.encode(request.password()));
        user.setRole(request.role());
        return response(users.save(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = users.findByEmailIgnoreCase(normaliseEmail(request.email()))
                .orElseThrow(this::invalidCredentials);
        if (!passwords.matches(request.password(), user.getPasswordHash())) {
            throw invalidCredentials();
        }
        return response(user);
    }

    private ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    private String normaliseEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private AuthResponse response(User user) {
        return new AuthResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
