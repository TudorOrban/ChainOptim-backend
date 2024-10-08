package org.chainoptim.core.tenant.subscription.repository;

import org.chainoptim.core.tenant.subscription.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {

    List<SubscriptionPlan> findByOrganizationId(Integer organizationId);
}
