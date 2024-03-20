package org.chainoptim.features.factory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFactoryStageDTO {

    private Integer factoryId;
    private Integer stageId;
    private Float capacity;
    private Float duration;
    private Integer priority;
    private Float minimumRequiredCapacity;
}
