package org.chainoptim.features.productpipeline.dto;

import org.chainoptim.features.productpipeline.model.Stage;

public class StageDTOMapper {

    private StageDTOMapper() {}

    public static StagesSearchDTO convertStageToStagesSearchDTO(Stage stage) {
        StagesSearchDTO stageDTO = new StagesSearchDTO();
        stageDTO.setId(stage.getId());
        stageDTO.setName(stage.getName());
        stageDTO.setCreatedAt(stage.getCreatedAt());

        return stageDTO;
    }

    public static Stage convertCreateStageDTOToStage(CreateStageDTO stageDTO) {
        Stage stage = new Stage();
        stage.setOrganizationId(stageDTO.getOrganizationId());
        stage.setProductId(stageDTO.getProductId());
        stage.setName(stageDTO.getName());
        stage.setDescription(stageDTO.getDescription());

        return stage;
    }
}
