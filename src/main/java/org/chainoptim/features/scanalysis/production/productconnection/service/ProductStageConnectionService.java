package org.chainoptim.features.scanalysis.production.productconnection.service;

import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;
import org.chainoptim.features.scanalysis.production.productconnection.repository.ProductStageConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStageConnectionService {

    private final ProductStageConnectionRepository productStageConnectionRepository;

    @Autowired
    public ProductStageConnectionService(ProductStageConnectionRepository productStageConnectionRepository) {
        this.productStageConnectionRepository = productStageConnectionRepository;
    }

    public List<ProductStageConnection> getConnectionsByProductId(Integer productId) {
        return productStageConnectionRepository.findProductStageConnectionsByFactoryId(productId);
    }
}
