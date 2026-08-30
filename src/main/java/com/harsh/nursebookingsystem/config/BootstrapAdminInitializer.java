package com.harsh.nursebookingsystem.config;

import com.harsh.nursebookingsystem.user.domain.Role;
import com.harsh.nursebookingsystem.user.domain.User;
import com.harsh.nursebookingsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates the local development administrator on first application start.
 * Disable it before deploying by setting app.bootstrap-admin.enabled=false.
 */
@Component
@ConditionalOnProperty(prefix = "app.bootstrap-admin", name = "enabled", havingValue = "true")
public class BootstrapAdminInitializer implements CommandLineRunner {
    private final UserRepository users;
    private final String email;
    private final String password;
    private final BCryptPasswordEncoder passwords = new BCryptPasswordEncoder();

    public BootstrapAdminInitializer(UserRepository users,
                                     @Value("${app.bootstrap-admin.email}") String email,
                                     @Value("${app.bootstrap-admin.password}") String password) {
        this.users = users;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(String... args) {
        if (users.existsByEmailIgnoreCase(email)) {
            return;
        }

        User admin = new User();
        admin.setEmail(email);
        admin.setPasswordHash(passwords.encode(password));
        admin.setRole(Role.ADMIN);
        users.save(admin);
    }
}
