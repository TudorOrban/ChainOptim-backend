package org.chainoptim.features.client.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.features.client.repository.ClientShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientShipmentServiceImpl implements ClientShipmentService {

    private final ClientShipmentRepository clientShipmentRepository;

    @Autowired
    public ClientShipmentServiceImpl(ClientShipmentRepository clientShipmentRepository) {
        this.clientShipmentRepository = clientShipmentRepository;
    }

    public ClientShipment getClientShipmentById(Integer shipmentId) {
        return clientShipmentRepository.findById(shipmentId).orElseThrow(() -> new ResourceNotFoundException("Client shipment with ID: " + shipmentId + " not found."));
    }

    public List<ClientShipment> getClientShipmentByClientOrderId(Integer orderId) {
        return clientShipmentRepository.findByClientOrderId(orderId);
    }
}
