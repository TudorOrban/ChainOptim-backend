package org.chainoptim.features.demand.dto;

import org.chainoptim.shared.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientOrderDTO {

    private Integer clientId;
    private Integer productId;
    private Integer organizationId;
    private String companyId;
    private Float quantity;
    private Float deliveredQuantity;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
}
