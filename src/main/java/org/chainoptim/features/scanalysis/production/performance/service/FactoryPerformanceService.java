package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;

public interface FactoryPerformanceService {

    FactoryPerformanceReport computeFactoryPerformanceReport(FactoryProductionHistory productionHistory, FactoryGraph factoryGraph);
}
