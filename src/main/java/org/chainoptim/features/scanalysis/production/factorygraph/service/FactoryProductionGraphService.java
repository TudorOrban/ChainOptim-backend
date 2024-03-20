package org.chainoptim.features.scanalysis.production.factorygraph.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryProductionGraph;

import java.util.List;

public interface FactoryProductionGraphService {

    List<FactoryProductionGraph> getProductionGraphByFactoryId(Integer factoryId);
    FactoryGraph createFactoryGraph(FactoryGraph factoryGraph, Integer factoryId);
    FactoryProductionGraph generateFactoryGraph(Integer factoryId);
    FactoryProductionGraph updateFactoryGraph(Integer factoryId);
    FactoryGraph saveFactoryGraph(Integer productionGraphId, FactoryGraph factoryGraph);
}
