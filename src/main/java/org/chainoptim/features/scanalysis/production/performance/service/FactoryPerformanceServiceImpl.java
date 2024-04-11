package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;
import org.springframework.stereotype.Service;

@Service
public class FactoryPerformanceServiceImpl implements FactoryPerformanceService {

    public FactoryPerformanceReport computeFactoryPerformanceReport(Integer factoryId) {
        FactoryPerformanceReport factoryPerformanceReport = new FactoryPerformanceReport();


        return factoryPerformanceReport;
    }
}
