package org.chainoptim.features.production.stage.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryStageSearchDTO {
    private Integer id;
    private Integer factoryId;
    private Integer stageId;
    private String stageName;
}
