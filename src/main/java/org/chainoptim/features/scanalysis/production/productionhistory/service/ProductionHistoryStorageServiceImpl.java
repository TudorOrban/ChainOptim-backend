package org.chainoptim.features.scanalysis.production.productionhistory.service;

import org.chainoptim.core.general.s3.service.S3JsonService;
import org.chainoptim.features.scanalysis.production.productionhistory.model.ProductionHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHistoryStorageServiceImpl implements ProductionHistoryStorageService {

    private final S3JsonService s3JsonService;

    private static final String PRODUCTION_HISTORY_BUCKET = "chainoptim-productionhistory";

    @Autowired
    public ProductionHistoryStorageServiceImpl(S3JsonService s3JsonService) {
        this.s3JsonService = s3JsonService;
    }

    public String saveProductionHistory(Integer factoryId, ProductionHistory history) {
        String key = generateS3Key(factoryId);
        s3JsonService.uploadObject(PRODUCTION_HISTORY_BUCKET, key, history);
        return key;
    }

    public ProductionHistory getProductionHistory(Integer factoryId) {
        String key = generateS3Key(factoryId);
        return s3JsonService.downloadObject(PRODUCTION_HISTORY_BUCKET, key, ProductionHistory.class);
    }

    public void deleteProductionHistory(Integer factoryId) {
        String key = generateS3Key(factoryId);
        s3JsonService.deleteObject(PRODUCTION_HISTORY_BUCKET, key);
    }

    private String generateS3Key(Integer factoryId) {
        return "productionHistories/factory-" + factoryId;
    }
}
