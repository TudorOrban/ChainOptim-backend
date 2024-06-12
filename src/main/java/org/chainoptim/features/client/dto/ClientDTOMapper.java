package org.chainoptim.features.client.dto;

import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.shared.commonfeatures.location.model.Location;

public class ClientDTOMapper {

    private ClientDTOMapper() {}

    public static ClientsSearchDTO convertToClientsSearchDTO(Client client) {
        ClientsSearchDTO dto = new ClientsSearchDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());
        dto.setLocation(client.getLocation());
        return dto;
    }

    public static Client convertCreateClientDTOToClient(CreateClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setOrganizationId(clientDTO.getOrganizationId());
        if (clientDTO.getLocationId() != null) {
            Location location = new Location();
            location.setId(clientDTO.getLocationId());
            client.setLocation(location);
        }

        return client;
    }

    public static ClientOrder mapCreateDtoToClientOrder(CreateClientOrderDTO order) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientId(order.getClientId());
        clientOrder.setOrganizationId(order.getOrganizationId());
        clientOrder.setCompanyId(order.getCompanyId());
        clientOrder.setQuantity(order.getQuantity());
        clientOrder.setDeliveredQuantity(order.getDeliveredQuantity());
        clientOrder.setOrderDate(order.getOrderDate());
        clientOrder.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        clientOrder.setDeliveryDate(order.getDeliveryDate());
        clientOrder.setStatus(order.getStatus());

        return clientOrder;
    }

    public static void setUpdateClientOrderDTOToClientOrder(ClientOrder clientOrder, UpdateClientOrderDTO orderDTO) {
        clientOrder.setCompanyId(orderDTO.getCompanyId());
        clientOrder.setQuantity(orderDTO.getQuantity());
        clientOrder.setOrderDate(orderDTO.getOrderDate());
        clientOrder.setEstimatedDeliveryDate(orderDTO.getEstimatedDeliveryDate());
        clientOrder.setDeliveryDate(orderDTO.getDeliveryDate());
        clientOrder.setStatus(orderDTO.getStatus());
        clientOrder.setDeliveredQuantity(orderDTO.getDeliveredQuantity());
    }

    public static ClientShipment mapCreateClientShipmentDTOTOShipment(CreateClientShipmentDTO shipmentDTO) {
        ClientShipment shipment = new ClientShipment();
        shipment.setOrganizationId(shipmentDTO.getOrganizationId());
        shipment.setClientId(shipmentDTO.getClientId());
        shipment.setClientOrderId(shipmentDTO.getClientOrderId());
        shipment.setQuantity(shipmentDTO.getQuantity());
        shipment.setShipmentStartingDate(shipmentDTO.getShipmentStartingDate());
        shipment.setEstimatedArrivalDate(shipmentDTO.getEstimatedArrivalDate());
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setStatus(shipmentDTO.getStatus());
        shipment.setCurrentLocationLatitude(shipmentDTO.getCurrentLocationLatitude());
        shipment.setCurrentLocationLongitude(shipmentDTO.getCurrentLocationLongitude());

        return shipment;
    }

    public static void setUpdateClientShipmentDTOToClientShipment(ClientShipment shipment, UpdateClientShipmentDTO shipmentDTO) {
        shipment.setQuantity(shipmentDTO.getQuantity());
        shipment.setShipmentStartingDate(shipmentDTO.getShipmentStartingDate());
        shipment.setEstimatedArrivalDate(shipmentDTO.getEstimatedArrivalDate());
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setStatus(shipmentDTO.getStatus());
        shipment.setCurrentLocationLatitude(shipmentDTO.getCurrentLocationLatitude());
        shipment.setCurrentLocationLongitude(shipmentDTO.getCurrentLocationLongitude());
    }
}
