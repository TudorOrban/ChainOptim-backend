package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryInventoryRepository extends JpaRepository<FactoryInventoryItem, Integer>, FactoryInventorySearchRepository {

    List<FactoryInventoryItem> findByOrganizationId(Integer organizationId);

    List<FactoryInventoryItem> findByFactoryId(Integer factoryId);

    @Query("SELECT COUNT(ii) FROM FactoryInventoryItem ii WHERE ii.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}