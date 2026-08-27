package com.harsh.nursebookingsystem.auth.web;

import com.harsh.nursebookingsystem.user.domain.Role;

import java.util.UUID;

public record AuthResponse(UUID userId, String email, Role role) {
}
