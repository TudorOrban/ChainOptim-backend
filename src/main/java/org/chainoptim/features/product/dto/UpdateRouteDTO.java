package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.TransportRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRouteDTO {

    private Integer id;
    private Integer organizationId;
    private TransportRoute transportRoute;
}
