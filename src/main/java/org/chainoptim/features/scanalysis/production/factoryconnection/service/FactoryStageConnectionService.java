package org.chainoptim.features.scanalysis.production.factoryconnection.service;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;

import java.util.List;

public interface FactoryStageConnectionService {

    List<FactoryStageConnection> getConnectionsByFactoryId(Integer factoryId);
}
