package org.chainoptim.features.scanalysis.production.productionhistory.dto;

import org.chainoptim.features.scanalysis.production.productionhistory.model.ProductionHistory;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFactoryProductionHistoryDTO {

    private Integer factoryId;
    private ProductionHistory productionHistory;
}
