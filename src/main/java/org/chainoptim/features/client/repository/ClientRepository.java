package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer>, ClientsSearchRepository {

    List<Client> findByOrganizationId(Integer organizationId);

    @Query("SELECT c.organizationId FROM Client c WHERE c.id = :clientId")
    Optional<Integer> findOrganizationIdById(@Param("clientId") Long clientId);

    @Query("SELECT c FROM Client c WHERE c.name = :name")
    Optional<Client> findByName(@Param("name") String name);

    long countByOrganizationId(Integer organizationId);
}
