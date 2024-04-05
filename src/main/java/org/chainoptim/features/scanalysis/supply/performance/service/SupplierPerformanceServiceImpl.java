package org.chainoptim.features.scanalysis.supply.performance.service;

import org.chainoptim.features.scanalysis.supply.performance.model.ComponentDeliveryPerformance;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierShipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierPerformanceServiceImpl implements SupplierPerformanceService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final SupplierShipmentRepository supplierShipmentRepository;

    @Autowired
    public SupplierPerformanceServiceImpl(SupplierOrderRepository supplierOrderRepository,
                                          SupplierShipmentRepository supplierShipmentRepository) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierShipmentRepository = supplierShipmentRepository;
    }

    public SupplierPerformanceReport computeSupplierPerformanceReport(Integer supplierId) {
        List<SupplierOrder> supplierOrders = supplierOrderRepository.findBySupplierId(supplierId);
        List<SupplierShipment> supplierShipments = supplierShipmentRepository.findBySupplierOrderIds(
                supplierOrders.stream().map(SupplierOrder::getId).toList());

        SupplierPerformanceReport report = new SupplierPerformanceReport();

        int totalDeliveredOrders = 0;
        float totalDelays = 0;
        float ratioOfOnTimeOrderDeliveries = 0;
        float ratioOfOnTimeShipmentDeliveries = 0;
        float averageShipmentsPerOrder = 0;
        float averageTimeToShipOrder = 0;

        List<ComponentDeliveryPerformance> componentPerformances = new ArrayList<>();

        // Calculate performance metrics
        for (SupplierOrder supplierOrder : supplierOrders) {
            if (supplierOrder.getDeliveryDate() == null) continue;

            totalDeliveredOrders++;

            Duration orderDelay = Duration.between(supplierOrder.getDeliveryDate(), supplierOrder.getEstimatedDeliveryDate());
            totalDelays += orderDelay.toDays();
            if (totalDelays <= 0) ratioOfOnTimeOrderDeliveries++;

            Duration shipDuration = Duration.between(supplierOrder.getOrderDate(), supplierOrder.getDeliveryDate());
            averageTimeToShipOrder += shipDuration.toDays();

            List<SupplierShipment> correspondingShipments = supplierShipments.stream()
                    .filter(ss -> ss.getSupplierOrderId().equals(supplierOrder.getId()))
                    .toList();

            averageShipmentsPerOrder += correspondingShipments.size();
        }

        // Calculate average metrics
        if (totalDeliveredOrders > 0) {
            report.setTotalDeliveredOrders(totalDeliveredOrders);
            report.setTotalDelays(totalDelays);
            report.setAverageDelayPerOrder(totalDelays / totalDeliveredOrders);
            report.setRatioOfOnTimeOrderDeliveries(ratioOfOnTimeOrderDeliveries / totalDeliveredOrders);
            report.setAverageDelayPerShipment(totalDelays / totalDeliveredOrders);
            report.setRatioOfOnTimeShipmentDeliveries(ratioOfOnTimeShipmentDeliveries / totalDeliveredOrders);
            report.setAverageShipmentsPerOrder(averageShipmentsPerOrder / totalDeliveredOrders);
            report.setAverageTimeToShipOrder(averageTimeToShipOrder / totalDeliveredOrders);
        }

        return report;
    }
}
