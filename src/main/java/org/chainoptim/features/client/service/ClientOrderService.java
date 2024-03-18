package org.chainoptim.features.client.service;

import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;

import java.util.List;

public interface ClientOrderService {

    List<ClientOrder> getClientOrdersByOrganizationId(Integer organizationId);
    List<ClientOrder> getClientOrdersByClientId(Integer clientId);

    ClientOrder saveOrUpdateClientOrder(CreateClientOrderDTO order);
}
