package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.TransportRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRouteDTO {

    private Integer organizationId;
    private String companyId;
    private TransportRoute transportRoute;
}
