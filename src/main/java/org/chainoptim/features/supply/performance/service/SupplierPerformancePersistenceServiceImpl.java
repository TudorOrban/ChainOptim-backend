package org.chainoptim.features.supply.performance.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.supply.performance.dto.CreateSupplierPerformanceDTO;
import org.chainoptim.features.supply.performance.dto.SupplierPerformanceDTOMapper;
import org.chainoptim.features.supply.performance.dto.UpdateSupplierPerformanceDTO;
import org.chainoptim.features.supply.performance.model.SupplierPerformance;
import org.chainoptim.features.supply.performance.model.SupplierPerformanceReport;
import org.chainoptim.features.supply.performance.repository.SupplierPerformanceRepository;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierPerformancePersistenceServiceImpl implements SupplierPerformancePersistenceService {

    private final SupplierPerformanceRepository supplierPerformanceRepository;
    private final SupplierPerformanceService supplierPerformanceService;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierPerformancePersistenceServiceImpl(SupplierPerformanceRepository supplierPerformanceRepository,
                                                     SupplierPerformanceService supplierPerformanceService,
                                                     SupplierRepository supplierRepository) {
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierPerformanceService = supplierPerformanceService;
        this.supplierRepository = supplierRepository;
    }

    public SupplierPerformance getSupplierPerformance(Integer supplierId) {
        return supplierPerformanceRepository.findBySupplierId(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier performance for supplier ID: " + supplierId + " not found"));
    }

    public SupplierPerformance refreshSupplierPerformance(Integer supplierId) {
        // Compute fresh supplier performance report
        SupplierPerformanceReport supplierPerformanceReport = supplierPerformanceService.computeSupplierPerformanceReport(supplierId);

        updateSupplier(supplierId, supplierPerformanceReport);

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

    private void updateSupplier(Integer supplierId, SupplierPerformanceReport supplierPerformanceReport) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + supplierId + " not found"));
        supplier.setOverallScore(supplierPerformanceReport.getOverallScore());
        supplier.setTimelinessScore(supplierPerformanceReport.getTimelinessScore());
        supplier.setQuantityPerTimeScore(supplierPerformanceReport.getQuantityPerTimeScore());
        supplier.setAvailabilityScore(supplierPerformanceReport.getAvailabilityScore());
        supplier.setQualityScore(supplierPerformanceReport.getQualityScore());

        supplierRepository.save(supplier);
    }
}
