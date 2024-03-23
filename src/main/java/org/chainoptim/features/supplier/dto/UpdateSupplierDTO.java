package org.chainoptim.features.supplier.dto;

import lombok.Data;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;

@Data
public class UpdateSupplierDTO {

    private Integer id;
    private String name;
    private Integer locationId;
    private CreateLocationDTO location;
    private boolean createLocation;
}
