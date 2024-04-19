package org.chainoptim.features.client.service;


import org.chainoptim.features.client.dto.CreateClientShipmentDTO;
import org.chainoptim.features.client.dto.UpdateClientShipmentDTO;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ClientShipmentService {

    List<ClientShipment> getClientShipmentByClientOrderId(Integer orderId);
    PaginatedResults<ClientShipment> getClientShipmentsByClientOrderIdAdvanced(Integer clientOrderId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    ClientShipment getClientShipmentById(Integer shipmentId);
    ClientShipment createClientShipment(CreateClientShipmentDTO shipmentDTO);
    List<ClientShipment> createClientShipmentsInBulk(List<CreateClientShipmentDTO> shipmentDTOs);
    List<ClientShipment> updateClientShipmentsInBulk(List<UpdateClientShipmentDTO> shipmentDTOs);
    List<Integer> deleteClientShipmentsInBulk(List<Integer> shipmentIds);
}
