package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryInventoryItemRepository extends JpaRepository<FactoryInventoryItem, Integer>, FactoryInventoryItemSearchRepository {

    List<FactoryInventoryItem> findByOrganizationId(Integer organizationId);

    List<FactoryInventoryItem> findByFactoryId(Integer factoryId);

    @Query("SELECT ii.organizationId FROM FactoryInventoryItem ii WHERE ii.id = :id")
    Optional<Integer> findOrganizationIdById(@Param("id") Long id);

    @Query("SELECT ii FROM FactoryInventoryItem ii WHERE ii.id IN :ids")
    Optional<List<FactoryInventoryItem>> findByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT COUNT(ii) FROM FactoryInventoryItem ii WHERE ii.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}