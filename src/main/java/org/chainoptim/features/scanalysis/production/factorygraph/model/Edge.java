package org.chainoptim.features.scanalysis.production.factorygraph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    Integer incomingFactoryStageId;
    Integer incomingStageOutputId;
    Integer outgoingFactoryStageId;
    Integer outgoingStageInputId;
}
