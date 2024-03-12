package org.chainoptim.features.supply.dto;

import lombok.Data;
import org.chainoptim.features.supply.model.SupplierOrder;

import java.time.LocalDateTime;

@Data
public class CreateSupplierOrderDTO {

    private Integer organizationId;
    private Integer supplierId;
    private Integer componentId;
    private Float quantity;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
    private SupplierOrder.Status status;
}
