package org.chainoptim.features.scanalysis.supply.performance.dto;

import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupplierPerformanceDTO {

    private Integer supplierId;
    private SupplierPerformanceReport report;
}
