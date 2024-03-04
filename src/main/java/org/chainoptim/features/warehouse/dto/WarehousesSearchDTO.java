package org.chainoptim.features.warehouse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.shared.location.model.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WarehousesSearchDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Location location;
}
