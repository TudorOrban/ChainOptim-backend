package org.chainoptim.core.user.repository;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByOrganizationId(Integer organizationId);

    List<UserSearchResultDTO> findByUsernameContaining(String username);
}
