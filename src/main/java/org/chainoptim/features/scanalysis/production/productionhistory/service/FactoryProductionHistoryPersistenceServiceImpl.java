package org.chainoptim.features.scanalysis.production.productionhistory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.AddDayToFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.CreateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.FactoryProductionHistoryDTOMapper;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.UpdateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.scanalysis.production.productionhistory.repository.FactoryProductionHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryProductionHistoryPersistenceServiceImpl implements FactoryProductionHistoryPersistenceService {

    private final FactoryProductionHistoryRepository productionHistoryRepository;

    @Autowired
    public FactoryProductionHistoryPersistenceServiceImpl(FactoryProductionHistoryRepository productionHistoryRepository) {
        this.productionHistoryRepository = productionHistoryRepository;
    }

    public FactoryProductionHistory getFactoryProductionHistoryByFactoryId(Integer factoryId) {
        return productionHistoryRepository.findByFactoryId(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier performance for factory ID: " + factoryId + " not found"));
    }

//    public SupplierPerformance refreshSupplierPerformance(Integer supplierId) {
//        // Compute fresh supplier performance report
//        SupplierPerformanceReport supplierPerformanceReport = supplierPerformanceService.computeSupplierPerformanceReport(supplierId);
//
//        SupplierPerformance supplierPerformance = supplierPerformanceRepository.findBySupplierId(supplierId)
//                .orElse(null);
//
//        // Create new supplier performance or update existing one
//        if (supplierPerformance == null) {
//            CreateSupplierPerformanceDTO performanceDTO = new CreateSupplierPerformanceDTO();
//            performanceDTO.setSupplierId(supplierId);
//            performanceDTO.setReport(supplierPerformanceReport);
//            return createSupplierPerformance(performanceDTO);
//        } else {
//            UpdateSupplierPerformanceDTO performanceDTO = new UpdateSupplierPerformanceDTO();
//            performanceDTO.setId(supplierPerformance.getId());
//            performanceDTO.setSupplierId(supplierId);
//            performanceDTO.setReport(supplierPerformanceReport);
//            return updateSupplierPerformance(performanceDTO);
//        }
//    }

    public FactoryProductionHistory createFactoryProductionHistory(CreateFactoryProductionHistoryDTO historyDTO) {
        return productionHistoryRepository.save(FactoryProductionHistoryDTOMapper
                .mapCreateFactoryProductionHistoryDTOToFactoryProductionHistory(historyDTO));
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

        return productionHistoryRepository.save(history);
    }

    public void deleteFactoryProductionHistory(Integer id) {
        productionHistoryRepository.deleteById(id);
    }
}