package org.chainoptim.features.production.analysis.productionhistory.dto;

import org.chainoptim.features.production.analysis.productionhistory.model.ProductionHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFactoryProductionHistoryDTO {

    private Integer id;
    private Integer factoryId;
    private String s3Key;
    private ProductionHistory productionHistory;
}
