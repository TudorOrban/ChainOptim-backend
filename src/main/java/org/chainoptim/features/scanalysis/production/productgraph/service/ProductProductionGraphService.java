package org.chainoptim.features.scanalysis.production.productgraph.service;

import org.chainoptim.features.scanalysis.production.productgraph.model.ProductGraph;
import org.chainoptim.features.scanalysis.production.productgraph.model.ProductProductionGraph;

import java.util.List;

public interface ProductProductionGraphService {

    List<ProductProductionGraph> getProductionGraphByProductId(Integer productId);
    ProductGraph createProductGraph(ProductGraph productGraph, Integer productId);
//    ProductProductionGraph updateProductGraph(Integer productId);
    ProductGraph saveProductGraph(Integer productionGraphId, ProductGraph productGraph);
}
