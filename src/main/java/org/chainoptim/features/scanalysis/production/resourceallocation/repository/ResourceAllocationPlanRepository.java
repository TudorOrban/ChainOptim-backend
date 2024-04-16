package org.chainoptim.features.scanalysis.production.resourceallocation.repository;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocationPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceAllocationPlanRepository extends JpaRepository<ResourceAllocationPlan, Integer> {

    Optional<ResourceAllocationPlan> findByFactoryId(Integer factoryId);
}
