package org.chainoptim.features.factory.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
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
class FactoryServiceTest {

    @Mock
    private FactoryRepository factoryRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private FactoryServiceImpl factoryService;

    @Test
    void testCreateFactory() {
        // Arrange
        CreateFactoryDTO factoryDTO = new CreateFactoryDTO("Test Factory", 1, 1, new CreateLocationDTO(), false);
        Factory expectedFactory = FactoryDTOMapper.convertCreateFactoryDTOToFactory(factoryDTO);

        when(factoryRepository.save(any(Factory.class))).thenReturn(expectedFactory);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateFactoryDTO(any(CreateFactoryDTO.class))).thenReturn(factoryDTO);

        // Act
        Factory createdFactory = factoryService.createFactory(factoryDTO);

        // Assert
        assertNotNull(createdFactory);
        assertEquals(expectedFactory.getName(), createdFactory.getName());
        assertEquals(expectedFactory.getOrganizationId(), createdFactory.getOrganizationId());
        assertEquals(expectedFactory.getLocation().getId(), createdFactory.getLocation().getId());

        verify(factoryRepository, times(1)).save(any(Factory.class));
    }

    @Test
    void testUpdateFactory_ExistingFactory() {
        // Arrange
        UpdateFactoryDTO factoryDTO = new UpdateFactoryDTO(1, "Test Factory", 1, new CreateLocationDTO(), false);
        Factory existingFactory = new Factory();
        existingFactory.setId(1);

        when(factoryRepository.findById(1)).thenReturn(java.util.Optional.of(existingFactory));
        when(factoryRepository.save(any(Factory.class))).thenReturn(existingFactory);
        when(entitySanitizerService.sanitizeUpdateFactoryDTO(any(UpdateFactoryDTO.class))).thenReturn(factoryDTO);

        // Act
        Factory updatedFactory = factoryService.updateFactory(factoryDTO);

        // Assert
        assertNotNull(updatedFactory);
        assertEquals(existingFactory.getName(), updatedFactory.getName());
        assertEquals(existingFactory.getOrganizationId(), updatedFactory.getOrganizationId());
        assertEquals(existingFactory.getLocation().getId(), updatedFactory.getLocation().getId());

        verify(factoryRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateFactory_NonExistingFactory() {
        // Arrange
        UpdateFactoryDTO factoryDTO = new UpdateFactoryDTO(1, "Test Factory", 1, new CreateLocationDTO(), false);
        Factory existingFactory = new Factory();
        existingFactory.setId(1);

        when(factoryRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateFactoryDTO(any(UpdateFactoryDTO.class))).thenReturn(factoryDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> factoryService.updateFactory(factoryDTO));

        verify(factoryRepository, times(1)).findById(1);
        verify(factoryRepository, never()).save(any(Factory.class));
    }

    @Test
    void testDeleteFactory() {
        // Arrange
        Integer factoryId = 1;
        doNothing().when(factoryRepository).delete(any(Factory.class));

        // Act
        factoryService.deleteFactory(1);

        // Assert
        verify(factoryRepository, times(1)).delete(any(Factory.class));
    }
}
