package org.chainoptim.features.production.factorygraph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    Integer srcFactoryStageId;
    Integer srcStageOutputId;
    Integer destFactoryStageId;
    Integer destStageInputId;
}
