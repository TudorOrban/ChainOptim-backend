package org.chainoptim.features.goods.stageconnection.service;

import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;
import org.chainoptim.features.goods.stageconnection.repository.StageConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageConnectionServiceImpl implements StageConnectionService {

    private final StageConnectionRepository stageConnectionRepository;

    @Autowired
    public StageConnectionServiceImpl(StageConnectionRepository stageConnectionRepository) {
        this.stageConnectionRepository = stageConnectionRepository;
    }

    public List<ProductStageConnection> getConnectionsByProductId(Integer productId) {
        return stageConnectionRepository.findProductStageConnectionsByProductId(productId);
    }
}
