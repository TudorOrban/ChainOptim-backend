package org.chainoptim.features.factory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FactoriesSearchDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Location location;
}
