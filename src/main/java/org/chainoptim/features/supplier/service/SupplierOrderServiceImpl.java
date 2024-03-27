package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final KafkaSupplierOrderService kafkaSupplierOrderService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierOrderServiceImpl(
            SupplierOrderRepository supplierOrderRepository,
            KafkaSupplierOrderService kafkaSupplierOrderService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.kafkaSupplierOrderService = kafkaSupplierOrderService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId) {
        return supplierOrderRepository.findByOrganizationId(organizationId);
    }

    public List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId);
    }

    public PaginatedResults<SupplierOrder> getSuppliersBySupplierIdAdvanced(Integer supplierId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return supplierOrderRepository.findBySupplierIdAdvanced(supplierId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    // Create
    public SupplierOrder saveOrUpdateSupplierOrder(CreateSupplierOrderDTO orderDTO) {
        System.out.println("Sending order: " + orderDTO.getSupplierId());
        CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
        SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);
        // Publish order to Kafka broker on create or update
        kafkaSupplierOrderService.sendSupplierOrder(savedOrder);
        return savedOrder;
    }


}
