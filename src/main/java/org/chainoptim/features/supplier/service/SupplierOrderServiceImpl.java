package org.chainoptim.features.supplier.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final KafkaSupplierOrderServiceImpl kafkaSupplierOrderService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierOrderServiceImpl(
            SupplierOrderRepository supplierOrderRepository,
            KafkaSupplierOrderServiceImpl kafkaSupplierOrderService,
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
    public SupplierOrder createSupplierOrder(CreateSupplierOrderDTO orderDTO) {
        CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
        SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);
        // Publish order to Kafka broker on create or update
        kafkaSupplierOrderService.sendSupplierOrder(savedOrder);
        return savedOrder;
    }

    public List<SupplierOrder> createSupplierOrdersInBulk(List<CreateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> orders = orderDTOs.stream()
                .map(SupplierDTOMapper::mapCreateDtoToSupplierOrder)
                .toList();

        return supplierOrderRepository.saveAll(orders);
    }

    @Transactional
    public List<SupplierOrder> updateSuppliersOrdersInBulk(List<UpdateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> orders = new ArrayList<>();
        for (UpdateSupplierOrderDTO orderDTO : orderDTOs) {
            SupplierOrder order = supplierOrderRepository.findById(orderDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier Order with ID: " + orderDTO.getId() + " not found."));

            SupplierDTOMapper.setUpdateSupplierOrderDTOToClientOrder(order, orderDTO);
            orders.add(order);
        }

        return supplierOrderRepository.saveAll(orders);
    }

}
