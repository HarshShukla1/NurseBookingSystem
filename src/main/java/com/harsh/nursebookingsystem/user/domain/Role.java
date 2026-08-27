package com.harsh.nursebookingsystem.user.domain;

/**
 * The application role assigned to a user account.
 *
 * A role will later be used by Spring Security to decide which endpoints a
 * signed-in user is permitted to access.
 */
public enum Role {
    PATIENT,
    NURSE,
    ADMIN
}
