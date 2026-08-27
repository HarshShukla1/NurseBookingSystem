package com.harsh.nursebookingsystem.nurse.domain;

/**
 * The result of the administrator's licence and profile review.
 *
 * Storing an enum instead of a true/false value preserves the important
 * difference between a profile that is waiting to be reviewed and one that
 * has been rejected.
 */
public enum VerificationStatus {
    PENDING,
    APPROVED,
    REJECTED
}
