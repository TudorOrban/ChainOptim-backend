package org.chainoptim.features.goods.productgraph.service;

import org.chainoptim.features.goods.productgraph.model.ProductGraph;
import org.chainoptim.features.goods.productgraph.model.ProductProductionGraph;

import java.util.List;

public interface ProductProductionGraphService {

    List<ProductProductionGraph> getProductionGraphByProductId(Integer productId);
    ProductGraph createProductGraph(ProductGraph productGraph, Integer productId);
    ProductProductionGraph generateProductGraph(Integer productId);
    ProductProductionGraph updateProductGraph(Integer productId);
    ProductGraph saveProductGraph(Integer productionGraphId, ProductGraph productGraph);
}
