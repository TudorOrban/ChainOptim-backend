package org.chainoptim.core.tenant.user.repository;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.dto.UserSearchResultDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserSearchRepository {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.customRole cr WHERE u.username = :username")
    Optional<User> findByUsername(String username);
    List<User> findByOrganizationId(Integer organizationId);

    @Query("SELECT u.organization.id FROM User u WHERE u.id = :userId")
    Optional<Integer> findOrganizationIdById(@Param("userId") String userId);

    @Query("SELECT u FROM User u WHERE u.customRole.id = :roleId")
    List<User> findByCustomRoleId(Integer roleId);
    List<UserSearchResultDTO> findByUsernameContaining(String username);

    @Query("SELECT u FROM User u WHERE u.verificationToken = :token")
    Optional<User> findByVerificationToken(@Param("token") String token);
    @Query("SELECT u FROM User u WHERE u.verificationTokenExpirationDate < :now AND u.enabled = false")
    List<User> findByTokenExpirationDateBefore(@Param("now") LocalDateTime now);

    long countByOrganizationId(Integer organizationId);

}
