package org.chainoptim.features.productpipeline.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StageServiceTest {

    @Mock
    private StageRepository stageRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private StageServiceImpl stageService;

    @Test
    void testCreateStage() {
        // Arrange
        CreateStageDTO stageDTO = new CreateStageDTO(1, 1, "Test Stage", "Test Description");
        Stage expectedStage = StageDTOMapper.convertCreateStageDTOToStage(stageDTO);

        when(stageRepository.save(any(Stage.class))).thenReturn(expectedStage);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateStageDTO(any(CreateStageDTO.class))).thenReturn(stageDTO);

        // Act
        Stage createdStage = stageService.createStage(stageDTO);

        // Assert
        assertNotNull(createdStage);
        assertEquals(expectedStage.getName(), createdStage.getName());
        assertEquals(expectedStage.getDescription(), createdStage.getDescription());
        assertEquals(expectedStage.getProductId(), createdStage.getProductId());
    }

    @Test
    void testUpdateStage_ExistingStage() {
        // Arrange
        UpdateStageDTO stageDTO = new UpdateStageDTO(1, "Test Stage", "Test Description");
        Stage existingStage = new Stage();
        existingStage.setId(1);

        when(stageRepository.findById(1)).thenReturn(Optional.of(existingStage));
        when(stageRepository.save(any(Stage.class))).thenReturn(existingStage);
        when(entitySanitizerService.sanitizeUpdateStageDTO(any(UpdateStageDTO.class))).thenReturn(stageDTO);

        // Act
        Stage updatedStage = stageService.updateStage(stageDTO);

        // Assert
        assertNotNull(updatedStage);
        assertEquals(existingStage.getName(), updatedStage.getName());
        assertEquals(existingStage.getOrganizationId(), updatedStage.getOrganizationId());

        verify(stageRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateStage_NonExistingStage() {
        // Arrange
        UpdateStageDTO stageDTO = new UpdateStageDTO(1, "Test Stage", "Test Description");
        Stage existingStage = new Stage();
        existingStage.setId(1);

        when(stageRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateStageDTO(any(UpdateStageDTO.class))).thenReturn(stageDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> stageService.updateStage(stageDTO));

        verify(stageRepository, times(1)).findById(1);
        verify(stageRepository, never()).save(any(Stage.class));
    }

    @Test
    void testDeleteStage() {
        // Arrange
        doNothing().when(stageRepository).delete(any(Stage.class));

        // Act
        stageService.deleteStage(1);

        // Assert
        verify(stageRepository, times(1)).delete(any(Stage.class));
    }
}
