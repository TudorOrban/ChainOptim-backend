package org.chainoptim.features.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverviewDTO {

    private List<String> stageNames;
    private List<String> factoryNames;
    private List<String> warehouseNames;
    private List<String> clientNames;
}
