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
    private Integer productId;
    private Set<StageInput> stageInputs;
    private Set<StageOutput> stageOutputs;
}
