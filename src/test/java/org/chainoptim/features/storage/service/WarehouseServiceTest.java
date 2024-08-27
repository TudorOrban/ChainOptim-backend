package org.chainoptim.features.storage.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.storage.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.dto.WarehouseDTOMapper;
import org.chainoptim.features.storage.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.model.Warehouse;
import org.chainoptim.features.storage.repository.WarehouseRepository;
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
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Test
    void testCreateWarehouse() {
        // Arrange
        CreateWarehouseDTO warehouseDTO = new CreateWarehouseDTO("Test Warehouse", 1, 1, new CreateLocationDTO(), false);
        Warehouse expectedWarehouse = WarehouseDTOMapper.mapCreateWarehouseDTOToWarehouse(warehouseDTO);

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(expectedWarehouse);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateWarehouseDTO(any(CreateWarehouseDTO.class))).thenReturn(warehouseDTO);

        // Act
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouseDTO);

        // Assert
        assertNotNull(createdWarehouse);
        assertEquals(expectedWarehouse.getName(), createdWarehouse.getName());
        assertEquals(expectedWarehouse.getOrganizationId(), createdWarehouse.getOrganizationId());
        assertEquals(expectedWarehouse.getLocation().getId(), createdWarehouse.getLocation().getId());

        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

    @Test
    void testUpdateWarehouse_ExistingWarehouse() {
        // Arrange
        UpdateWarehouseDTO warehouseDTO = new UpdateWarehouseDTO(1, "Test Warehouse", 1, new CreateLocationDTO(), false);
        Warehouse existingWarehouse = new Warehouse();
        existingWarehouse.setId(1);

        when(warehouseRepository.findById(1)).thenReturn(Optional.of(existingWarehouse));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(existingWarehouse);
        when(entitySanitizerService.sanitizeUpdateWarehouseDTO(any(UpdateWarehouseDTO.class))).thenReturn(warehouseDTO);

        // Act
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(warehouseDTO);

        // Assert
        assertNotNull(updatedWarehouse);
        assertEquals(existingWarehouse.getName(), updatedWarehouse.getName());
        assertEquals(existingWarehouse.getOrganizationId(), updatedWarehouse.getOrganizationId());
        assertEquals(existingWarehouse.getLocation().getId(), updatedWarehouse.getLocation().getId());

        verify(warehouseRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateWarehouse_NonExistingWarehouse() {
        // Arrange
        UpdateWarehouseDTO warehouseDTO = new UpdateWarehouseDTO(1, "Test Warehouse", 1, new CreateLocationDTO(), false);
        Warehouse existingWarehouse = new Warehouse();
        existingWarehouse.setId(1);

        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateWarehouseDTO(any(UpdateWarehouseDTO.class))).thenReturn(warehouseDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> warehouseService.updateWarehouse(warehouseDTO));

        verify(warehouseRepository, times(1)).findById(1);
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }

    @Test
    void testDeleteWarehouse() {
        // Arrange
        doNothing().when(warehouseRepository).delete(any(Warehouse.class));

        // Act
        warehouseService.deleteWarehouse(1);

        // Assert
        verify(warehouseRepository, times(1)).delete(any(Warehouse.class));
    }
}
