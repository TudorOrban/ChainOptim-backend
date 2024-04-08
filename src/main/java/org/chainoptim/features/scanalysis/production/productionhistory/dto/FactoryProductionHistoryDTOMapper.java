package org.chainoptim.features.scanalysis.production.productionhistory.dto;

import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;

public class FactoryProductionHistoryDTOMapper {

    private FactoryProductionHistoryDTOMapper() {}

    public static FactoryProductionHistory mapCreateFactoryProductionHistoryDTOToFactoryProductionHistory(CreateFactoryProductionHistoryDTO dto) {
        FactoryProductionHistory factoryProductionHistory = new FactoryProductionHistory();
        factoryProductionHistory.setFactoryId(dto.getFactoryId());
        factoryProductionHistory.setProductionHistory(dto.getProductionHistory());

        return factoryProductionHistory;
    }

    public static void setUpdateFactoryProductionHistoryDTOToFactoryProductionHistory(UpdateFactoryProductionHistoryDTO dto, FactoryProductionHistory factoryProductionHistory) {
        factoryProductionHistory.setFactoryId(dto.getFactoryId());
        factoryProductionHistory.setProductionHistory(dto.getProductionHistory());
    }
}
