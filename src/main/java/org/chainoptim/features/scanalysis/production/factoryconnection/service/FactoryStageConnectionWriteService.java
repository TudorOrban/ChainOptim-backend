package org.chainoptim.features.scanalysis.production.factoryconnection.service;

import org.chainoptim.features.scanalysis.production.factoryconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;

import java.util.List;

public interface FactoryStageConnectionWriteService {

    FactoryStageConnection createConnection(CreateConnectionDTO connectionDTO);
    FactoryStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO);
    void deleteConnection(Integer connectionId);
    void findAndDeleteConnection(DeleteConnectionDTO connectionDTO);
}
