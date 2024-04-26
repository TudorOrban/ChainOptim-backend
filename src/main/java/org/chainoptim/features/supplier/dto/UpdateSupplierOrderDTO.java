package org.chainoptim.features.supplier.dto;

import org.chainoptim.shared.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSupplierOrderDTO {

    private Integer id;
    private Integer organizationId;
    private Integer componentId;
    private Float quantity;
    private Float deliveredQuantity;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
    private OrderStatus status;
    private String companyId;
}
