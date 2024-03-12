package org.chainoptim.features.evaluation.production.graph.service;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;

public interface FactoryProductionGraphService {

    FactoryGraph saveFactoryGraph(Integer productionGraphId, FactoryGraph factoryGraph);
}
