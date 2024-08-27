package org.chainoptim.features.production.factorygraph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmallStageOutput {
    private Integer id;
    private Integer componentId;
    private String componentName;
    private Integer productId;
    private String productName;
    private Float quantityPerStage;
    private Float expectedOutputPerAllocation;
    private Float outputPerRequest;
}