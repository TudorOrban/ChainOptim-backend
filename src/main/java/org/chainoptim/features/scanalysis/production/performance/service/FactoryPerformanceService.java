package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;

public interface FactoryPerformanceService {

    FactoryPerformanceReport computeFactoryPerformanceReport(Integer factoryId);
}
