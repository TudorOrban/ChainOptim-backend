package org.chainoptim.features.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWarehouseDTO {

    private String name;
    private Integer organizationId;
    private Integer locationId;
    private CreateLocationDTO location;
    private boolean createLocation;
}
