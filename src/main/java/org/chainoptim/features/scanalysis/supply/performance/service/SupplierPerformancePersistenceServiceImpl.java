package org.chainoptim.features.scanalysis.supply.performance.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.supply.performance.dto.CreateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.dto.SupplierPerformanceDTOMapper;
import org.chainoptim.features.scanalysis.supply.performance.dto.UpdateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformance;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import org.chainoptim.features.scanalysis.supply.performance.repository.SupplierPerformanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierPerformancePersistenceServiceImpl implements SupplierPerformancePersistenceService {

    private final SupplierPerformanceRepository supplierPerformanceRepository;
    private final SupplierPerformanceService supplierPerformanceService;

    @Autowired
    public SupplierPerformancePersistenceServiceImpl(SupplierPerformanceRepository supplierPerformanceRepository,
                                                     SupplierPerformanceService supplierPerformanceService) {
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierPerformanceService = supplierPerformanceService;
    }

    public SupplierPerformance getSupplierPerformance(Integer supplierId) {
        return supplierPerformanceRepository.findBySupplierId(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier performance for supplier ID: " + supplierId + " not found"));
    }

    public SupplierPerformance refreshSupplierPerformance(Integer supplierId) {
        // Compute fresh supplier performance report
        SupplierPerformanceReport supplierPerformanceReport = supplierPerformanceService.computeSupplierPerformanceReport(supplierId);

        SupplierPerformance supplierPerformance = supplierPerformanceRepository.findBySupplierId(supplierId)
                .orElse(null);

        // Create new supplier performance or update existing one
        if (supplierPerformance == null) {
            CreateSupplierPerformanceDTO performanceDTO = new CreateSupplierPerformanceDTO();
            performanceDTO.setSupplierId(supplierId);
            performanceDTO.setReport(supplierPerformanceReport);
            return createSupplierPerformance(performanceDTO);
        } else {
            UpdateSupplierPerformanceDTO performanceDTO = new UpdateSupplierPerformanceDTO();
            performanceDTO.setId(supplierPerformance.getId());
            performanceDTO.setSupplierId(supplierId);
            performanceDTO.setReport(supplierPerformanceReport);
            return updateSupplierPerformance(performanceDTO);
        }
    }
    public SupplierPerformance createSupplierPerformance(CreateSupplierPerformanceDTO performanceDTO) {
        return supplierPerformanceRepository.save(SupplierPerformanceDTOMapper.mapCreateSupplierPerformanceDTOToSupplierPerformance(performanceDTO));
    }

    public SupplierPerformance updateSupplierPerformance(UpdateSupplierPerformanceDTO performanceDTO) {
        SupplierPerformance performance = supplierPerformanceRepository.findById(performanceDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier performance with ID: " + performanceDTO.getId() + " not found"));
        SupplierPerformanceDTOMapper.setUpdateSupplierPerformanceDTOToSupplierPerformance(performanceDTO, performance);

        return supplierPerformanceRepository.save(performance);
    }

    public void deleteSupplierPerformance(Integer id) {
        supplierPerformanceRepository.deleteById(id);
    }
}
