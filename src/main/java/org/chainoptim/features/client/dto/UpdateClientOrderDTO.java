package org.chainoptim.features.client.dto;

import org.chainoptim.shared.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientOrderDTO {

    private Integer id;
    private Integer organizationId;
    private Integer productId;
    private String companyId;
    private Float quantity;
    private Float deliveredQuantity;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
}
