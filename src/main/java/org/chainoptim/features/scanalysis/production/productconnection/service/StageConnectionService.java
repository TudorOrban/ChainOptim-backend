package org.chainoptim.features.scanalysis.production.productconnection.service;

import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;

import java.util.List;

public interface StageConnectionService {

    List<ProductStageConnection> getConnectionsByProductId(Integer productId);
}
