package org.chainoptim.features.production.analysis.performance.service;

import org.chainoptim.features.production.analysis.performance.dto.CreateFactoryPerformanceDTO;
import org.chainoptim.features.production.analysis.performance.dto.UpdateFactoryPerformanceDTO;
import org.chainoptim.features.production.analysis.performance.model.FactoryPerformance;

public interface FactoryPerformancePersistenceService {

    FactoryPerformance getFactoryPerformance(Integer factoryId);
    FactoryPerformance createFactoryPerformance(CreateFactoryPerformanceDTO performanceDTO);
    FactoryPerformance updateFactoryPerformance(UpdateFactoryPerformanceDTO performanceDTO);
    void deleteFactoryPerformance(Integer id);

    FactoryPerformance refreshFactoryPerformance(Integer factoryId);
}
