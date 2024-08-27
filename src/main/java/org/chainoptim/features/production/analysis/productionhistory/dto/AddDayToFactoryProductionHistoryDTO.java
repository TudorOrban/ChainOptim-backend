package org.chainoptim.features.production.analysis.productionhistory.dto;

import org.chainoptim.features.production.analysis.productionhistory.model.DailyProductionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDayToFactoryProductionHistoryDTO {

    private Integer id;
    private Integer factoryId;
    private float daysSinceStart;
    private DailyProductionRecord dailyProductionRecord;
    private boolean shouldComputeResults;
}
