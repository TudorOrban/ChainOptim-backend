package org.chainoptim.features.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSupplierDTO {

    private Integer id;
    private String name;
    private Integer locationId;
    private CreateLocationDTO location;
    private boolean createLocation;
}
