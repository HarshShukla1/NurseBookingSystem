package com.harsh.nursebookingsystem.user.repository;

import com.harsh.nursebookingsystem.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JpaRepository supplies standard methods such as save, findById, findAll,
 * and deleteById. No implementation class is needed—Spring creates one at runtime.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
}
