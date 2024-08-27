package org.chainoptim.features.production.analysis.resourceallocation.repository;

import org.chainoptim.features.production.analysis.resourceallocation.model.ResourceAllocationPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceAllocationPlanRepository extends JpaRepository<ResourceAllocationPlan, Integer> {

    Optional<ResourceAllocationPlan> findByFactoryId(Integer factoryId);
}
