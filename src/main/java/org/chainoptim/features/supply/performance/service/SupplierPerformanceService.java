package org.chainoptim.features.supply.performance.service;

import org.chainoptim.features.supply.performance.model.SupplierPerformanceReport;

public interface SupplierPerformanceService {

    SupplierPerformanceReport computeSupplierPerformanceReport(Integer supplierId);
}
