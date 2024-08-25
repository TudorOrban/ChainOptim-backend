package org.chainoptim.core.subscription.repository;

import org.chainoptim.core.subscription.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {

    List<SubscriptionPlan> findByOrganizationId(Integer organizationId);
}
