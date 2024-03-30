package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, ClientsSearchRepository {

    List<Client> findByOrganizationId(Integer organizationId);

    @Query("SELECT c.organizationId FROM Client c WHERE c.id = :clientId")
    Optional<Integer> findOrganizationIdById(@Param("clientId") Long clientId);

    long countByOrganizationId(Integer organizationId);
}
