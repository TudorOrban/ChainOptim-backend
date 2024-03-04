package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer>, FactoriesSearchRepository {

    List<Factory> findByOrganizationId(Integer organizationId);
}