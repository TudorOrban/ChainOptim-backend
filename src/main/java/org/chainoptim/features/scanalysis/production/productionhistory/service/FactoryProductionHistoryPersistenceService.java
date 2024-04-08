package org.chainoptim.features.scanalysis.production.productionhistory.service;

import org.chainoptim.features.scanalysis.production.productionhistory.dto.CreateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.UpdateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;

public interface FactoryProductionHistoryPersistenceService {

    FactoryProductionHistory getFactoryProductionHistoryByFactoryId(Integer factoryId);
    FactoryProductionHistory createFactoryProductionHistory(CreateFactoryProductionHistoryDTO historyDTO);
    FactoryProductionHistory updateFactoryProductionHistory(UpdateFactoryProductionHistoryDTO historyDTO);
    void deleteFactoryProductionHistory(Integer id);
}
