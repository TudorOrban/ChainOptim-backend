package org.chainoptim.features.production.analysis.productionhistory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.production.analysis.productionhistory.dto.AddDayToFactoryProductionHistoryDTO;
import org.chainoptim.features.production.analysis.productionhistory.dto.CreateFactoryProductionHistoryDTO;
import org.chainoptim.features.production.analysis.productionhistory.dto.FactoryProductionHistoryDTOMapper;
import org.chainoptim.features.production.analysis.productionhistory.dto.UpdateFactoryProductionHistoryDTO;
import org.chainoptim.features.production.analysis.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.production.analysis.productionhistory.model.ProductionHistory;
import org.chainoptim.features.production.analysis.productionhistory.repository.FactoryProductionHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FactoryProductionHistoryPersistenceServiceImpl implements FactoryProductionHistoryPersistenceService {

    private final FactoryProductionHistoryRepository productionHistoryRepository;
    private final ProductionHistoryStorageService historyStorageService;

    @Value("${app.environment}")
    private String appEnvironment;

    @Autowired
    public FactoryProductionHistoryPersistenceServiceImpl(FactoryProductionHistoryRepository productionHistoryRepository,
                                                          ProductionHistoryStorageService historyStorageService) {
        this.productionHistoryRepository = productionHistoryRepository;
        this.historyStorageService = historyStorageService;
    }

    public FactoryProductionHistory getFactoryProductionHistoryByFactoryId(Integer factoryId) {
        FactoryProductionHistory factoryProductionHistory = productionHistoryRepository.findByFactoryId(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Production History for factory ID: " + factoryId + " not found"));

        if (!Objects.equals(appEnvironment, "prod")) {
            return factoryProductionHistory;
        }

        // Get production history from S3 only when in production
        ProductionHistory productionHistory = historyStorageService.getProductionHistory(factoryId);
        factoryProductionHistory.setProductionHistory(productionHistory);
        return factoryProductionHistory;
    }

    public Integer getIdByFactoryId(Integer factoryId) {
        return productionHistoryRepository.findIdByFactoryId(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Production History for factory ID: " + factoryId + " not found"));
    }

    public FactoryProductionHistory createFactoryProductionHistory(CreateFactoryProductionHistoryDTO historyDTO) {
        FactoryProductionHistory factoryProductionHistory = productionHistoryRepository.save(FactoryProductionHistoryDTOMapper
                .mapCreateFactoryProductionHistoryDTOToFactoryProductionHistory(historyDTO));

        if (!Objects.equals(appEnvironment, "prod")) {
            return factoryProductionHistory;
        }

        // Save production history to S3 only when in production
        historyStorageService.saveProductionHistory(factoryProductionHistory.getFactoryId(), factoryProductionHistory.getProductionHistory());
        return factoryProductionHistory;
    }

    public FactoryProductionHistory updateFactoryProductionHistory(UpdateFactoryProductionHistoryDTO historyDTO) {
        FactoryProductionHistory history = productionHistoryRepository.findById(historyDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Production history with ID: " + historyDTO.getId() + " not found"));
        FactoryProductionHistoryDTOMapper.setUpdateFactoryProductionHistoryDTOToFactoryProductionHistory(historyDTO, history);

        return productionHistoryRepository.save(history);
    }

    public FactoryProductionHistory addDayToFactoryProductionHistory(AddDayToFactoryProductionHistoryDTO addDayDTO) {
        FactoryProductionHistory history = productionHistoryRepository.findById(addDayDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Production history with ID: " + addDayDTO.getId() + " not found"));

        FactoryProductionHistoryDTOMapper.addDayToFactoryProductionHistory(addDayDTO, history);

        FactoryProductionHistory updatedHistory = productionHistoryRepository.save(history);

        if (!Objects.equals(appEnvironment, "prod")) {
            return updatedHistory;
        }

        // Save production history to S3 only when in production
        historyStorageService.saveProductionHistory(updatedHistory.getFactoryId(), updatedHistory.getProductionHistory());
        return updatedHistory;
    }

    public void deleteFactoryProductionHistory(Integer id) {
        Integer factoryId = productionHistoryRepository.findFactoryIdById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production history with ID: " + id + " not found"));

        if (!Objects.equals(appEnvironment, "prod")) {
            productionHistoryRepository.deleteById(id);
            return;
        }

        // Delete production history from S3 only when in production
        historyStorageService.deleteProductionHistory(factoryId);
    }
}
