package org.chainoptim.features.production.analysis.productionhistory.service;

import org.chainoptim.features.production.analysis.productionhistory.model.ProductionHistory;

public interface ProductionHistoryStorageService {

    String saveProductionHistory(Integer factoryId, ProductionHistory history);
    ProductionHistory getProductionHistory(Integer factoryId);
    void deleteProductionHistory(Integer factoryId);
}
