package org.chainoptim.features.productpipeline.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.controller.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.unit.model.NewUnitOfMeasurement;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.component.dto.ComponentDTOMapper;
import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.features.goods.component.service.ComponentServiceImpl;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComponentServiceTest {

    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private ComponentServiceImpl componentService;

    @Test
    void testCreateComponent() {
        // Arrange
        CreateComponentDTO componentDTO = new CreateComponentDTO("Test Component", "Test Description", 1, 1, new CreateUnitOfMeasurementDTO(), false, new NewUnitOfMeasurement());
        Component expectedComponent = ComponentDTOMapper.convertCreateComponentDTOToComponent(componentDTO);

        when(componentRepository.save(any(Component.class))).thenReturn(expectedComponent);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateComponentDTO(any(CreateComponentDTO.class))).thenReturn(componentDTO);

        // Act
        Component createdComponent = componentService.createComponent(componentDTO);

        // Assert
        assertNotNull(createdComponent);
        assertEquals(expectedComponent.getName(), createdComponent.getName());
        assertEquals(expectedComponent.getDescription(), createdComponent.getDescription());
        assertEquals(expectedComponent.getOrganizationId(), createdComponent.getOrganizationId());
        assertEquals(expectedComponent.getUnit().getId(), createdComponent.getUnit().getId());
    }

    @Test
    void testUpdateComponent_ExistingComponent() {
        // Arrange
        UpdateComponentDTO componentDTO = new UpdateComponentDTO(1, "Test Component", "Test Description", 1, new NewUnitOfMeasurement());
        Component existingComponent = new Component();
        existingComponent.setId(1);

        when(componentRepository.findById(1)).thenReturn(Optional.of(existingComponent));
        when(componentRepository.save(any(Component.class))).thenReturn(existingComponent);
        when(entitySanitizerService.sanitizeUpdateComponentDTO(any(UpdateComponentDTO.class))).thenReturn(componentDTO);

        // Act
        Component updatedComponent = componentService.updateComponent(componentDTO);

        // Assert
        assertNotNull(updatedComponent);
        assertEquals(existingComponent.getName(), updatedComponent.getName());
        assertEquals(existingComponent.getOrganizationId(), updatedComponent.getOrganizationId());

        verify(componentRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateComponent_NonExistingComponent() {
        // Arrange
        UpdateComponentDTO componentDTO = new UpdateComponentDTO(1, "Test Component", "Test Description", 1, new NewUnitOfMeasurement());
        Component existingComponent = new Component();
        existingComponent.setId(1);

        when(componentRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateComponentDTO(any(UpdateComponentDTO.class))).thenReturn(componentDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> componentService.updateComponent(componentDTO));

        verify(componentRepository, times(1)).findById(1);
        verify(componentRepository, never()).save(any(Component.class));
    }

    @Test
    void testDeleteComponent() {
        // Arrange
        doNothing().when(componentRepository).deleteById(any(Integer.class));

        // Act
        componentService.deleteComponent(1);

        // Assert
        verify(componentRepository, times(1)).deleteById(any(Integer.class));
    }
}
