package org.chainoptim.features.supplier.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.UpdateSupplierDTO;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.repository.SupplierRepository;
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
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    void testCreateSupplier() {
        // Arrange
        CreateSupplierDTO supplierDTO = new CreateSupplierDTO("Test Supplier", 1, 1, new CreateLocationDTO(), false);
        Supplier expectedSupplier = SupplierDTOMapper.convertCreateSupplierDTOToSupplier(supplierDTO);

        when(supplierRepository.save(any(Supplier.class))).thenReturn(expectedSupplier);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateSupplierDTO(any(CreateSupplierDTO.class))).thenReturn(supplierDTO);

        // Act
        Supplier createdSupplier = supplierService.createSupplier(supplierDTO);

        // Assert
        assertNotNull(createdSupplier);
        assertEquals(expectedSupplier.getName(), createdSupplier.getName());
        assertEquals(expectedSupplier.getOrganizationId(), createdSupplier.getOrganizationId());
        assertEquals(expectedSupplier.getLocation().getId(), createdSupplier.getLocation().getId());

        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testUpdateSupplier_ExistingSupplier() {
        // Arrange
        UpdateSupplierDTO supplierDTO = new UpdateSupplierDTO(1, "Test Supplier", 1, new CreateLocationDTO(), false);
        Supplier existingSupplier = new Supplier();
        existingSupplier.setId(1);

        when(supplierRepository.findById(1)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(existingSupplier);
        when(entitySanitizerService.sanitizeUpdateSupplierDTO(any(UpdateSupplierDTO.class))).thenReturn(supplierDTO);

        // Act
        Supplier updatedSupplier = supplierService.updateSupplier(supplierDTO);

        // Assert
        assertNotNull(updatedSupplier);
        assertEquals(existingSupplier.getName(), updatedSupplier.getName());
        assertEquals(existingSupplier.getOrganizationId(), updatedSupplier.getOrganizationId());
        assertEquals(existingSupplier.getLocation().getId(), updatedSupplier.getLocation().getId());

        verify(supplierRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateSupplier_NonExistingSupplier() {
        // Arrange
        UpdateSupplierDTO supplierDTO = new UpdateSupplierDTO(1, "Test Supplier", 1, new CreateLocationDTO(), false);
        Supplier existingSupplier = new Supplier();
        existingSupplier.setId(1);

        when(supplierRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateSupplierDTO(any(UpdateSupplierDTO.class))).thenReturn(supplierDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> supplierService.updateSupplier(supplierDTO));

        verify(supplierRepository, times(1)).findById(1);
        verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    void testDeleteSupplier() {
        // Arrange
        doNothing().when(supplierRepository).delete(any(Supplier.class));

        // Act
        supplierService.deleteSupplier(1);

        // Assert
        verify(supplierRepository, times(1)).delete(any(Supplier.class));
    }
}
