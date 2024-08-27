package org.chainoptim.features.demand.clientshipment.service;


import org.chainoptim.features.demand.clientshipment.dto.CreateClientShipmentDTO;
import org.chainoptim.features.demand.clientshipment.dto.UpdateClientShipmentDTO;
import org.chainoptim.features.demand.clientshipment.model.ClientShipment;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface ClientShipmentService {

    List<ClientShipment> getClientShipmentByClientId(Integer clientId);
    PaginatedResults<ClientShipment> getClientShipmentsAdvanced(SearchMode searchMode, Integer entity, SearchParams searchParams);
    ClientShipment getClientShipmentById(Integer shipmentId);
    ClientShipment createClientShipment(CreateClientShipmentDTO shipmentDTO);
    List<ClientShipment> createClientShipmentsInBulk(List<CreateClientShipmentDTO> shipmentDTOs);
    List<ClientShipment> updateClientShipmentsInBulk(List<UpdateClientShipmentDTO> shipmentDTOs);
    List<Integer> deleteClientShipmentsInBulk(List<Integer> shipmentIds);
}
