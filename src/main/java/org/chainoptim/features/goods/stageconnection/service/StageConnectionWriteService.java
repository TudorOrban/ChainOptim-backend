package org.chainoptim.features.goods.stageconnection.service;

import org.chainoptim.features.goods.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;

public interface StageConnectionWriteService {

    ProductStageConnection createConnection(CreateConnectionDTO connectionDTO);
    ProductStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO);
    void deleteConnection(Integer connectionId);
    void findAndDeleteConnection(DeleteConnectionDTO connectionDTO);
}
