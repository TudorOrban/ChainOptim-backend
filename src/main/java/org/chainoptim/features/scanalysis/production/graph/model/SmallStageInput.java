package org.chainoptim.features.scanalysis.production.graph.model;

import lombok.Data;

@Data
public class SmallStageInput {
    Integer id;
    Integer componentId;
    Float quantityPerStage;
    Float allocatedQuantity;
    Float requestedQuantity;
}
