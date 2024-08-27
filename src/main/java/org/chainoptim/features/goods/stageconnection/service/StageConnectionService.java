package org.chainoptim.features.goods.stageconnection.service;

import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;

import java.util.List;

public interface StageConnectionService {

    List<ProductStageConnection> getConnectionsByProductId(Integer productId);
}
