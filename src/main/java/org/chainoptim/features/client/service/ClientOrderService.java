package org.chainoptim.features.client.service;

import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ClientOrderService {

    List<ClientOrder> getClientOrdersByOrganizationId(Integer organizationId);
    List<ClientOrder> getClientOrdersByClientId(Integer clientId);
    PaginatedResults<ClientOrder> getClientOrdersByClientIdAdvanced(Integer clientId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    ClientOrder saveOrUpdateClientOrder(CreateClientOrderDTO order);
}
