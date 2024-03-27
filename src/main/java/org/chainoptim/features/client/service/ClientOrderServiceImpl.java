package org.chainoptim.features.client.service;

import org.chainoptim.features.client.dto.ClientDTOMapper;
import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientOrderServiceImpl(
            ClientOrderRepository clientOrderRepository,
            EntitySanitizerService entitySanitizerService
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<ClientOrder> getClientOrdersByOrganizationId(Integer organizationId) {
        return clientOrderRepository.findByOrganizationId(organizationId);
    }

    public List<ClientOrder> getClientOrdersByClientId(Integer clientId) {
        return clientOrderRepository.findByClientId(clientId);
    }

    public PaginatedResults<ClientOrder> getClientOrdersByClientIdAdvanced(Integer clientId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return clientOrderRepository.findByClientIdAdvanced(clientId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }


    // Create
    public ClientOrder saveOrUpdateClientOrder(CreateClientOrderDTO orderDTO) {
        System.out.println("Sending order: " + orderDTO.getClientId());
        CreateClientOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateClientOrderDTO(orderDTO);
        ClientOrder clientOrder = ClientDTOMapper.mapCreateDtoToClientOrder(sanitizedOrderDTO);
        return clientOrderRepository.save(clientOrder);
    }


}
