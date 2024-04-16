package org.chainoptim.features.productpipeline.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.service.UnitOfMeasurementService;
import org.chainoptim.features.productpipeline.dto.ComponentDTOMapper;
import org.chainoptim.features.productpipeline.dto.ComponentsSearchDTO;
import org.chainoptim.features.productpipeline.dto.CreateComponentDTO;
import org.chainoptim.features.productpipeline.dto.UpdateComponentDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final UnitOfMeasurementService unitOfMeasurementService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ComponentServiceImpl(ComponentRepository componentRepository,
                                UnitOfMeasurementService unitOfMeasurementService,
                                SubscriptionPlanLimiterService planLimiterService,
                                EntitySanitizerService entitySanitizerService) {
        this.componentRepository = componentRepository;
        this.unitOfMeasurementService = unitOfMeasurementService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<Component> getComponentsByOrganizationId(Integer organizationId) {
        return componentRepository.findByOrganizationId(organizationId);
    }

    public List<ComponentsSearchDTO> getComponentsByOrganizationIdSmall(Integer organizationId) {
        return componentRepository.findByOrganizationIdSmall(organizationId);
    }

    // Create
    public Component createComponent(CreateComponentDTO componentDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(componentDTO.getOrganizationId(), Feature.COMPONENT, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed components for the current Subscription Plan.");
        }

        CreateComponentDTO sanitizedComponentDTO = entitySanitizerService.sanitizeCreateComponentDTO(componentDTO);

        // Create unit of measurement if requested
        if (sanitizedComponentDTO.isCreateUnit() && sanitizedComponentDTO.getUnitDTO() != null) {
            UnitOfMeasurement unitOfMeasurement = unitOfMeasurementService.createUnitOfMeasurement(sanitizedComponentDTO.getUnitDTO());
            Component component = ComponentDTOMapper.convertCreateComponentDTOToComponent(sanitizedComponentDTO);
            component.setUnit(unitOfMeasurement);
            return componentRepository.save(component);
        } else {
            return componentRepository.save(ComponentDTOMapper.convertCreateComponentDTOToComponent(sanitizedComponentDTO));
        }
    }

    // Update
    public Component updateComponent(UpdateComponentDTO componentDTO) {
        UpdateComponentDTO sanitizedComponentDTO = entitySanitizerService.sanitizeUpdateComponentDTO(componentDTO);
        Component component = componentRepository.findById(sanitizedComponentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + sanitizedComponentDTO.getId() + " not found."));

        component.setName(sanitizedComponentDTO.getName());
        component.setDescription(sanitizedComponentDTO.getDescription());

        return componentRepository.save(component);
    }

    // Delete
    public void deleteComponent(Integer id) {
        componentRepository.deleteById(id);
    }
}
