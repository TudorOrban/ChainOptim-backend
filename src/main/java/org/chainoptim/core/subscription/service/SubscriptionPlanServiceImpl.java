package org.chainoptim.core.subscription.service;

import org.chainoptim.core.subscription.dto.CreateSubscriptionPlanDTO;
import org.chainoptim.core.subscription.dto.SubscriptionPlanDTOMapper;
import org.chainoptim.core.subscription.dto.UpdateSubscriptionPlanDTO;
import org.chainoptim.core.subscription.model.SubscriptionPlan;
import org.chainoptim.core.subscription.repository.SubscriptionPlanRepository;
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

    public SubscriptionPlan getSubscriptionPlanByOrganizationId(Integer id) {
        return subscriptionPlanRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Subscription Plan with ID: " + id + " not found."));
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
