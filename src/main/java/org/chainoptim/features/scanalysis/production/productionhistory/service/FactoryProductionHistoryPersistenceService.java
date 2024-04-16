package org.chainoptim.features.scanalysis.production.productionhistory.service;

import org.chainoptim.features.scanalysis.production.productionhistory.dto.AddDayToFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.CreateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.UpdateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;

public interface FactoryProductionHistoryPersistenceService {

    FactoryProductionHistory getFactoryProductionHistoryByFactoryId(Integer factoryId);
    Integer getIdByFactoryId(Integer factoryId);
    FactoryProductionHistory createFactoryProductionHistory(CreateFactoryProductionHistoryDTO historyDTO);
    FactoryProductionHistory updateFactoryProductionHistory(UpdateFactoryProductionHistoryDTO historyDTO);
    FactoryProductionHistory addDayToFactoryProductionHistory(AddDayToFactoryProductionHistoryDTO addDayDTO);
    void deleteFactoryProductionHistory(Integer id);
}
