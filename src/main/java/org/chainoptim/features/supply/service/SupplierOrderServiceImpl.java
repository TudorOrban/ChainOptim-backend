package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.dto.SupplierDTOMapper;
import org.chainoptim.features.supply.model.SupplierOrder;
import org.chainoptim.features.supply.repository.SupplierOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final KafkaSupplierOrderService kafkaSupplierOrderService;

    @Autowired
    public SupplierOrderServiceImpl(
            SupplierOrderRepository supplierOrderRepository,
            KafkaSupplierOrderService kafkaSupplierOrderService
    ) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.kafkaSupplierOrderService = kafkaSupplierOrderService;
    }

    public List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId) {
        return supplierOrderRepository.findByOrganizationId(organizationId);
    }

    public List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId);
    }


    public SupplierOrder saveOrUpdateSupplierOrder(CreateSupplierOrderDTO order) {
        System.out.println("Sending order: " + order.getSupplierId());
        SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(order);
        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);
        // Publish order to Kafka broker on create or update
        kafkaSupplierOrderService.sendSupplierOrder(savedOrder);
        return savedOrder;
    }


}
