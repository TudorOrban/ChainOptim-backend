package com.TudorAOrban.chainoptimizer.organization.repository;

import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.users u WHERE o.id = :id")
    Optional<Organization> findByIdWithUsers(@Param("id") Integer id);

}
