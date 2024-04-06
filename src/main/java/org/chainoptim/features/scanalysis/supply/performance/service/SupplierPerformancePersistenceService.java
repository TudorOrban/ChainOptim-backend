package org.chainoptim.features.scanalysis.supply.performance.service;

import org.chainoptim.features.scanalysis.supply.performance.dto.CreateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.dto.UpdateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformance;

public interface SupplierPerformancePersistenceService {

    SupplierPerformance getSupplierPerformance(Integer supplierId);
    SupplierPerformance createSupplierPerformance(CreateSupplierPerformanceDTO performanceDTO);
    SupplierPerformance updateSupplierPerformance(UpdateSupplierPerformanceDTO performanceDTO);
    void deleteSupplierPerformance(Integer id);

    SupplierPerformance refreshSupplierPerformance(Integer supplierId);
}
