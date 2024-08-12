package org.chainoptim.features.scanalysis.production.productconnection.service;

import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;
import org.chainoptim.features.scanalysis.production.productconnection.repository.StageConnectionRepository;
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
