package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.performance.dto.CreateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.dto.UpdateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformance;

public interface FactoryPerformancePersistenceService {

    FactoryPerformance getFactoryPerformance(Integer factoryId);
    FactoryPerformance createFactoryPerformance(CreateFactoryPerformanceDTO performanceDTO);
    FactoryPerformance updateFactoryPerformance(UpdateFactoryPerformanceDTO performanceDTO);
    void deleteFactoryPerformance(Integer id);

    FactoryPerformance refreshFactoryPerformance(Integer factoryId);
}
