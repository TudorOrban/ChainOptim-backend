package org.chainoptim.features.scanalysis.production.productconnection.service;

import org.chainoptim.features.scanalysis.production.productconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.scanalysis.production.productconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.scanalysis.production.productconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;

public interface StageConnectionWriteService {

    ProductStageConnection createConnection(CreateConnectionDTO connectionDTO);
    ProductStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO);
    void deleteConnection(Integer connectionId);
    void findAndDeleteConnection(DeleteConnectionDTO connectionDTO);
}
