package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.core.tenant.subscription.dto.CreateSubscriptionPlanDTO;
import org.chainoptim.core.tenant.subscription.dto.SubscriptionPlanDTOMapper;
import org.chainoptim.core.tenant.subscription.dto.UpdateSubscriptionPlanDTO;
import org.chainoptim.core.tenant.subscription.model.SubscriptionPlan;
import org.chainoptim.core.tenant.subscription.repository.SubscriptionPlanRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public SubscriptionPlan getSubscriptionPlanByOrganizationId(Integer organizationId) {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findByOrganizationId(organizationId);
        if (plans.isEmpty()) {
            throw new ResourceNotFoundException("Subscription Plan with organization ID: " + organizationId + " not found.");
        }
        return plans.getFirst();
    }

    public SubscriptionPlan createSubscriptionPlan(CreateSubscriptionPlanDTO planDTO) {
        SubscriptionPlan plan = SubscriptionPlanDTOMapper.mapCreateSubscriptionPlanDTOToSubscriptionPlan(planDTO);

        List<SubscriptionPlan> existingPlans = subscriptionPlanRepository.findByOrganizationId(plan.getOrganizationId());
        if (!existingPlans.isEmpty()) {
            throw new IllegalArgumentException("Subscription Plan for Organization with ID: " + plan.getOrganizationId() + " already exists.");
        }

        return subscriptionPlanRepository.save(plan);
    }

    public SubscriptionPlan updateSubscriptionPlan(UpdateSubscriptionPlanDTO planDTO) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(planDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subscription Plan with ID: " + planDTO.getId() + " not found."));

        SubscriptionPlan updatedPlan = SubscriptionPlanDTOMapper.setUpdateSubscriptionPlanDTOToSubscriptionPlan(planDTO, plan);

        return subscriptionPlanRepository.save(updatedPlan);
    }

    public void deleteSubscriptionPlan(Integer id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Subscription Plan with ID: " + id + " not found."));

        subscriptionPlanRepository.delete(plan);
    }
}
