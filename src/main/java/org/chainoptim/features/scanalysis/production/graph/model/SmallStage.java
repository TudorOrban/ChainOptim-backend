package org.chainoptim.features.scanalysis.production.graph.model;

import lombok.Data;

import java.util.List;

@Data
public class SmallStage {
    Integer id;
    String stageName;
    List<SmallStageInput> stageInputs;
    List<SmallStageOutput> stageOutputs;
}