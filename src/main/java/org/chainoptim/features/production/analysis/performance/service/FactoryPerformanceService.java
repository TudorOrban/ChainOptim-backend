package org.chainoptim.features.production.analysis.performance.service;

import org.chainoptim.features.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.production.analysis.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.production.analysis.productionhistory.model.FactoryProductionHistory;

public interface FactoryPerformanceService {

    FactoryPerformanceReport computeFactoryPerformanceReport(FactoryProductionHistory productionHistory, FactoryGraph factoryGraph);
}
