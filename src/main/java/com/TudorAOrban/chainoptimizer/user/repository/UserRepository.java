package com.TudorAOrban.chainoptimizer.user.repository;

import com.TudorAOrban.chainoptimizer.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByOrganizationId(Integer organizationId);
}
