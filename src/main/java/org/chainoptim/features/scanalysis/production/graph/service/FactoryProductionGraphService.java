package org.chainoptim.features.scanalysis.production.graph.service;

import org.chainoptim.features.scanalysis.production.graph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.graph.model.FactoryProductionGraph;

import java.util.List;

public interface FactoryProductionGraphService {

    List<FactoryProductionGraph> getProductionGraphByFactoryId(Integer factoryId);
    FactoryGraph createFactoryGraph(FactoryGraph factoryGraph, Integer factoryId);
    FactoryGraph saveFactoryGraph(Integer productionGraphId, FactoryGraph factoryGraph);
}
