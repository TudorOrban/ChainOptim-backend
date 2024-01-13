package org.chainoptim.features.productpipeline.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.model.StageOutput;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StageDTO {
    private Integer id;
    private String name;
    // other fields
    private Integer productId; // Only the ID, not the entire Product object
    private Set<StageInput> stageInputs;
    private Set<StageOutput> stageOutputs;
}
