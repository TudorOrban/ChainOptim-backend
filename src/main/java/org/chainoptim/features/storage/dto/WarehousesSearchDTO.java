package org.chainoptim.features.storage.dto;

import lombok.*;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehousesSearchDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Location location;
}
