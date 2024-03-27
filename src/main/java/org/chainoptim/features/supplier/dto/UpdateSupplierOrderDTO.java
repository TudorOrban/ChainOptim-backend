package org.chainoptim.features.supplier.dto;

import org.chainoptim.features.supplier.model.SupplierOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSupplierOrderDTO {

    private Integer id;
    private Integer organizationId;
    private Integer componentId;
    private Float quantity;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
    private SupplierOrder.Status status;
    private String companyId;
}
