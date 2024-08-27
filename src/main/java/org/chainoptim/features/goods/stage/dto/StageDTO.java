package org.chainoptim.features.goods.stage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.features.goods.stage.model.StageInput;
import org.chainoptim.features.goods.stage.model.StageOutput;

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
