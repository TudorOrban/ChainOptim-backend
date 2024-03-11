package org.chainoptim.features.evaluation.production.graph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Edge {
    Integer incomingFactoryStageId;
    Integer incomingStageOutputId;
    Integer outgoingFactoryStageId;
    Integer outgoingStageInputId;
}
