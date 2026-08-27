package com.harsh.nursebookingsystem.nurse.web;

import com.harsh.nursebookingsystem.nurse.domain.VerificationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** The body an administrator sends after reviewing a nurse's credentials. */
public record ReviewVerificationRequest(
        @NotNull VerificationStatus status,
        @Size(max = 2000) String notes) {
}
