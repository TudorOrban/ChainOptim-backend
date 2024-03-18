package org.chainoptim.features.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateClientOrderDTO {

    private Integer clientId;
    private Integer productId;
    private Float quantity;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveryDate;
}
