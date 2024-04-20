package org.chainoptim.features.scanalysis.production.productionhistory.service;

import org.chainoptim.features.scanalysis.production.productionhistory.model.ProductionHistory;

public interface ProductionHistoryStorageService {

    String saveProductionHistory(Integer factoryId, ProductionHistory history);
    ProductionHistory getProductionHistory(Integer factoryId);
    void deleteProductionHistory(Integer factoryId);
}
