package org.chainoptim.core.organization.repository;

import org.chainoptim.core.organization.model.CustomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomRoleRepository extends JpaRepository<CustomRole, Integer> {

    @Query("SELECT cr FROM CustomRole cr WHERE cr.organizationId = :organizationId")
    List<CustomRole> findByOrganizationId(Integer organizationId);

    @Query("SELECT cr.organizationId FROM CustomRole cr WHERE cr.id = :customRoleId")
    Optional<Integer> findOrganizationIdById(@Param("customRoleId") Long customRoleId);
}
