package org.chainoptim.features.client.dto;

import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.model.ClientOrder;

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

        return client;
    }

    public static ClientOrder mapCreateDtoToClientOrder(CreateClientOrderDTO order) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientId(order.getClientId());
        clientOrder.setProductId(order.getProductId());
        clientOrder.setQuantity(order.getQuantity());
        clientOrder.setOrderDate(order.getOrderDate());
        clientOrder.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        clientOrder.setDeliveryDate(order.getDeliveryDate());

        return clientOrder;
    }
}
