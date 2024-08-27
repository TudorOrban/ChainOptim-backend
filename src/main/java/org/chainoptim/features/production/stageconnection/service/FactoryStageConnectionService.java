package org.chainoptim.features.production.stageconnection.service;

import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;

import java.util.List;

public interface FactoryStageConnectionService {

    List<FactoryStageConnection> getConnectionsByFactoryId(Integer factoryId);
}
