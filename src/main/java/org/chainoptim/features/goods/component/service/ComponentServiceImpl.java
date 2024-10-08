package org.chainoptim.features.goods.component.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.component.dto.ComponentDTOMapper;
import org.chainoptim.features.goods.component.dto.ComponentsSearchDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.component.dto.UpdateComponentDTO;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ComponentServiceImpl(ComponentRepository componentRepository,
                                SubscriptionPlanLimiterService planLimiterService,
                                EntitySanitizerService entitySanitizerService) {
        this.componentRepository = componentRepository;
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

    public PaginatedResults<ComponentsSearchDTO> getComponentsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Component> paginatedResults = componentRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
                paginatedResults.results.stream()
                        .map(ComponentDTOMapper::convertComponentToComponentsSearchDTO)
                        .toList(),
                paginatedResults.totalCount
        );
    }

    public Component getComponentById(Integer id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + id + " not found."));
    }

    // Create
    public Component createComponent(CreateComponentDTO componentDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(componentDTO.getOrganizationId(), Feature.COMPONENT, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed components for the current Subscription Plan.");
        }

        CreateComponentDTO sanitizedComponentDTO = entitySanitizerService.sanitizeCreateComponentDTO(componentDTO);

        return componentRepository.save(ComponentDTOMapper.convertCreateComponentDTOToComponent(sanitizedComponentDTO));
    }

    // Update
    public Component updateComponent(UpdateComponentDTO componentDTO) {
        UpdateComponentDTO sanitizedComponentDTO = entitySanitizerService.sanitizeUpdateComponentDTO(componentDTO);
        Component existingComponent = componentRepository.findById(sanitizedComponentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + sanitizedComponentDTO.getId() + " not found."));

        Component component = ComponentDTOMapper.setUpdateComponentDTOToComponent(existingComponent, sanitizedComponentDTO);

        return componentRepository.save(component);
    }

    // Delete
    public void deleteComponent(Integer id) {
        componentRepository.deleteById(id);
    }
}
