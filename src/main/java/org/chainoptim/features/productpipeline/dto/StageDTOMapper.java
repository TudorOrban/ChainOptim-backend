package org.chainoptim.features.productpipeline.dto;

import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.model.StageOutput;

public class StageDTOMapper {

    private StageDTOMapper() {}

    public static StagesSearchDTO convertStageToStagesSearchDTO(Stage stage) {
        StagesSearchDTO stageDTO = new StagesSearchDTO();
        stageDTO.setId(stage.getId());
        stageDTO.setName(stage.getName());
        stageDTO.setCreatedAt(stage.getCreatedAt());

        return stageDTO;
    }

    public static Stage mapCreateStageDTOToStage(CreateStageDTO stageDTO) {
        Stage stage = new Stage();
        stage.setOrganizationId(stageDTO.getOrganizationId());
        stage.setProductId(stageDTO.getProductId());
        stage.setName(stageDTO.getName());
        stage.setDescription(stageDTO.getDescription());

        return stage;
    }

    public static StageInput mapCreateStageInputDTOToStageInput(CreateStageInputDTO createStageInputDTO) {
        StageInput stageInput = new StageInput();

        stageInput.setQuantity(createStageInputDTO.getQuantity());

        return stageInput;
    }

    public static StageInput setUpdateStageInputDTOToStageInput(UpdateStageInputDTO updateStageInputDTO, StageInput stageInput) {
        stageInput.setQuantity(updateStageInputDTO.getQuantity());

        return stageInput;
    }

    public static StageOutput mapCreateStageOutputDTOToStageOutput(CreateStageOutputDTO createStageOutputDTO) {
        StageOutput stageOutput = new StageOutput();

        stageOutput.setQuantity(createStageOutputDTO.getQuantity());

        return stageOutput;
    }

    public static StageOutput setUpdateStageOutputDTOToStageOutput(UpdateStageOutputDTO updateStageOutputDTO, StageOutput stageOutput) {
        stageOutput.setQuantity(updateStageOutputDTO.getQuantity());

        return stageOutput;
    }
}
