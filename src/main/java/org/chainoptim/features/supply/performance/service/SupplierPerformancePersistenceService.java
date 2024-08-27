package org.chainoptim.features.supply.performance.service;

import org.chainoptim.features.supply.performance.dto.CreateSupplierPerformanceDTO;
import org.chainoptim.features.supply.performance.dto.UpdateSupplierPerformanceDTO;
import org.chainoptim.features.supply.performance.model.SupplierPerformance;

public interface SupplierPerformancePersistenceService {

    SupplierPerformance getSupplierPerformance(Integer supplierId);
    SupplierPerformance createSupplierPerformance(CreateSupplierPerformanceDTO performanceDTO);
    SupplierPerformance updateSupplierPerformance(UpdateSupplierPerformanceDTO performanceDTO);
    void deleteSupplierPerformance(Integer id);

    SupplierPerformance refreshSupplierPerformance(Integer supplierId);
}
