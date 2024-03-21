package org.chainoptim.features.scanalysis.production.factorygraph.model;

import lombok.Data;

@Data
public class SmallStageOutput {
    Integer id;
    Integer componentId;
    Float quantityPerStage;
    Float expectedOutputPerAllocation;
    Float outputPerRequest;
}