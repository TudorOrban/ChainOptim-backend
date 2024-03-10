package org.chainoptim.features.productpipeline.dto;

import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Stage;

public class StageDTOMapper {

    private StageDTOMapper() {}

    public static Stage convertCreateStageDTOToProduct(CreateStageDTO stageDTO) {
        Stage stage = new Stage();
        stage.setOrganizationId(stageDTO.getOrganizationId());
        stage.setProductId(stageDTO.getProductId());
        stage.setName(stageDTO.getName());
        stage.setDescription(stageDTO.getDescription());

        return stage;
    }
}
