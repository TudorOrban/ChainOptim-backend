package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.CreateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.PlanDTOMapper;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.UpdateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocationPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.repository.ResourceAllocationPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceAllocationPlanPersistenceServiceImpl implements ResourceAllocationPlanPersistenceService {

    private final ResourceAllocationPlanRepository resourceAllocationPlanRepository;

    @Autowired
    public ResourceAllocationPlanPersistenceServiceImpl(ResourceAllocationPlanRepository resourceAllocationPlanRepository) {
        this.resourceAllocationPlanRepository = resourceAllocationPlanRepository;
    }

    public ResourceAllocationPlan getResourceAllocationPlan(Integer factoryId) {
        return resourceAllocationPlanRepository.findByFactoryId(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource allocation plan for factory ID: " + factoryId + " not found"));
    }

    public ResourceAllocationPlan createResourceAllocationPlan(CreateAllocationPlanDTO planDTO) {
        return resourceAllocationPlanRepository.save(PlanDTOMapper.mapCreatePlanDTOToPlan(planDTO));
    }

    public ResourceAllocationPlan updateResourceAllocationPlan(UpdateAllocationPlanDTO planDTO) {
        ResourceAllocationPlan plan = resourceAllocationPlanRepository.findById(planDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource allocation plan with ID: " + planDTO.getId() + " not found"));
        PlanDTOMapper.setUpdatePlanDTOToPlan(planDTO, plan);

        return resourceAllocationPlanRepository.save(plan);
    }

    public void deleteResourceAllocationPlan(Integer id) {
        resourceAllocationPlanRepository.deleteById(id);
    }
}
