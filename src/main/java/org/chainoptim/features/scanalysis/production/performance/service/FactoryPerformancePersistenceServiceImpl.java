package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.production.performance.dto.CreateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.dto.FactoryPerformanceDTOMapper;
import org.chainoptim.features.scanalysis.production.performance.dto.UpdateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformance;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.scanalysis.production.performance.repository.FactoryPerformanceRepository;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.scanalysis.production.productionhistory.service.FactoryProductionHistoryPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryPerformancePersistenceServiceImpl implements FactoryPerformancePersistenceService {

    private final FactoryPerformanceRepository factoryPerformanceRepository;
    private final FactoryPerformanceService factoryPerformanceService;
    private final FactoryProductionHistoryPersistenceService historyPersistenceService;

    @Autowired
    public FactoryPerformancePersistenceServiceImpl(FactoryPerformanceRepository factoryPerformanceRepository,
                                                    FactoryPerformanceService factoryPerformanceService,
                                                    FactoryProductionHistoryPersistenceService historyPersistenceService) {
        this.factoryPerformanceRepository = factoryPerformanceRepository;
        this.factoryPerformanceService = factoryPerformanceService;
        this.historyPersistenceService = historyPersistenceService;
    }

    public FactoryPerformance getFactoryPerformance(Integer factoryId) {
        return factoryPerformanceRepository.findByFactoryId(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory performance for factory ID: " + factoryId + " not found"));
    }

    public FactoryPerformance refreshFactoryPerformance(Integer factoryId) {
        // Compute fresh factory performance report
        FactoryProductionHistory productionHistory = historyPersistenceService.getFactoryProductionHistoryByFactoryId(factoryId);

        FactoryPerformanceReport factoryPerformanceReport = factoryPerformanceService.computeFactoryPerformanceReport(productionHistory);

        FactoryPerformance factoryPerformance = factoryPerformanceRepository.findByFactoryId(factoryId)
                .orElse(null);

        // Create new factory performance or update existing one
        if (factoryPerformance == null) {
            CreateFactoryPerformanceDTO performanceDTO = new CreateFactoryPerformanceDTO();
            performanceDTO.setFactoryId(factoryId);
            performanceDTO.setReport(factoryPerformanceReport);
            return createFactoryPerformance(performanceDTO);
        } else {
            UpdateFactoryPerformanceDTO performanceDTO = new UpdateFactoryPerformanceDTO();
            performanceDTO.setId(factoryPerformance.getId());
            performanceDTO.setFactoryId(factoryId);
            performanceDTO.setReport(factoryPerformanceReport);
            return updateFactoryPerformance(performanceDTO);
        }
    }
    public FactoryPerformance createFactoryPerformance(CreateFactoryPerformanceDTO performanceDTO) {
        return factoryPerformanceRepository.save(FactoryPerformanceDTOMapper.mapCreateFactoryPerformanceDTOToFactoryPerformance(performanceDTO));
    }

    public FactoryPerformance updateFactoryPerformance(UpdateFactoryPerformanceDTO performanceDTO) {
        FactoryPerformance performance = factoryPerformanceRepository.findById(performanceDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Factory performance with ID: " + performanceDTO.getId() + " not found"));
        FactoryPerformanceDTOMapper.setUpdateFactoryPerformanceDTOToFactoryPerformance(performanceDTO, performance);

        return factoryPerformanceRepository.save(performance);
    }

    public void deleteFactoryPerformance(Integer id) {
        factoryPerformanceRepository.deleteById(id);
    }
}
