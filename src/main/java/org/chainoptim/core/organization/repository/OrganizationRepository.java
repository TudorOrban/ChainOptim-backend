package org.chainoptim.core.organization.repository;

import org.chainoptim.core.organization.dto.OrganizationSmallDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.users u WHERE o.id = :id")
    Optional<Organization> findByIdWithUsers(@Param("id") Integer id);

    @Query("SELECT o FROM Organization o " +
            "LEFT JOIN FETCH o.users u " +
            "LEFT JOIN FETCH u.customRole cr " +
            "WHERE o.id = :id")
    Optional<Organization> findByIdWithUsersAndCustomRoles(@Param("id") Integer id);

    @Query("SELECT o FROM Organization o WHERE o.name = :name")
    Optional<Organization> findByName(@Param("name") String name);

    @Query("SELECT o.subscriptionPlanTier FROM Organization o WHERE o.id = :id")
    Optional<SubscriptionPlanTier> getSubscriptionPlanTierById(Integer id);

    @Query("SELECT new org.chainoptim.core.organization.dto.OrganizationSmallDTO(o.id, o.name, o.createdAt, o.subscriptionPlanTier, o.isPlanBasic) FROM Organization o WHERE o.id = :id")
    Optional<OrganizationSmallDTO> getOrganizationSmallDTOById(Integer id);

    @Query("SELECT o.id FROM Organization o")
    List<Integer> getAllOrganizationIds();


}
