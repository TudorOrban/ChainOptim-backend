package org.chainoptim.features.goods.transportroute.dto;

import org.chainoptim.features.goods.transportroute.model.TransportRoute;
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
