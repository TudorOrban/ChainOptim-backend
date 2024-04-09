package org.chainoptim.features.scanalysis.supply.performance.service;

import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;

public interface SupplierPerformanceService {

    SupplierPerformanceReport computeSupplierPerformanceReport(Integer supplierId);
}
