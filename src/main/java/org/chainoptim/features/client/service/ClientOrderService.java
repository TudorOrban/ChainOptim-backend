package org.chainoptim.features.client.service;

import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.dto.UpdateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface ClientOrderService {

    List<ClientOrder> getClientOrdersByOrganizationId(Integer organizationId);
    List<ClientOrder> getClientOrdersByClientId(Integer clientId);
    PaginatedResults<ClientOrder> getClientOrdersAdvanced(SearchMode searchMode, Integer entity, SearchParams searchParams);

    ClientOrder createClientOrder(CreateClientOrderDTO order);
    List<ClientOrder> createClientOrdersInBulk(List<CreateClientOrderDTO> orderDTOs);
    List<ClientOrder> updateClientOrdersInBulk(List<UpdateClientOrderDTO> orderDTOs);
    List<Integer> deleteClientOrdersInBulk(List<Integer> orderIds);
}
