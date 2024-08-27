package org.chainoptim.features.production.stageconnection.service;

import org.chainoptim.features.production.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;

public interface FactoryStageConnectionWriteService {

    FactoryStageConnection createConnection(CreateConnectionDTO connectionDTO);
    FactoryStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO);
    void deleteConnection(Integer connectionId);
    void findAndDeleteConnection(DeleteConnectionDTO connectionDTO);
}
