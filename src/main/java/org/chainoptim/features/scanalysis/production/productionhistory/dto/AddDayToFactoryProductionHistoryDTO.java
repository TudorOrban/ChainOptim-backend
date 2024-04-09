package org.chainoptim.features.scanalysis.production.productionhistory.dto;

import org.chainoptim.features.scanalysis.production.productionhistory.model.DailyProductionRecord;
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
}
