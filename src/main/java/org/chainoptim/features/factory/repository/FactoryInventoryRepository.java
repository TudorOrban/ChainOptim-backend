package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryInventoryRepository extends JpaRepository<FactoryInventoryItem, Integer>, FactoryInventorySearchRepository {

    List<FactoryInventoryItem> findByFactoryId(Integer factoryId);
}