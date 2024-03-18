package org.chainoptim.features.client.service;


import org.chainoptim.features.client.model.ClientShipment;

import java.util.List;

public interface ClientShipmentService {

    ClientShipment getClientShipmentById(Integer shipmentId);
    List<ClientShipment> getClientShipmentByClientOrderId(Integer orderId);
}
