package org.chainoptim.features.productpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StagesSearchDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
}
