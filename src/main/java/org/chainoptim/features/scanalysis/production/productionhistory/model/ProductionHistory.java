package org.chainoptim.features.scanalysis.production.productionhistory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionHistory {

    private LocalDateTime startDate;
    private Map<Float, DailyProductionRecord> dailyProductionRecords;

}
