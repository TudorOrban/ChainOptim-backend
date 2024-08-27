package org.chainoptim.features.supply.performance.service;

import org.chainoptim.features.supply.performance.model.ComponentDeliveryPerformance;
import org.chainoptim.features.supply.performance.model.ComponentPerformanceMetrics;
import org.chainoptim.features.supply.performance.model.SupplierPerformanceReport;
import org.chainoptim.features.supply.model.SupplierOrder;
import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.features.supply.repository.SupplierOrderRepository;
import org.chainoptim.features.supply.repository.SupplierShipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
        Map<Integer, ComponentDeliveryPerformance> componentPerformances = new HashMap<>();

        float overallScore;
        float timelinessScore;
        float quantityPerTimeScore;
        float availabilityScore;
        float qualityScore = 0;

        int totalDeliveredOrders = 0;
        float totalDelays = 0;
        float ratioOfOnTimeOrderDeliveries = 0;
        float ratioOfOnTimeShipmentDeliveries = 0;
        float averageShipmentsPerOrder = 0;
        float averageTimeToShipOrder = 0;

        // Group orders by component
        Map<Integer, List<SupplierOrder>> ordersByComponent = new HashMap<>();
        for (SupplierOrder supplierOrder : supplierOrders) {
            Integer componentId = supplierOrder.getComponent().getId();
            if (ordersByComponent.containsKey(componentId)) {
                ordersByComponent.get(componentId).add(supplierOrder);
            } else {
                List<SupplierOrder> orders = new ArrayList<>();
                orders.add(supplierOrder);
                ordersByComponent.put(componentId, orders);
            }
        }

        for (Map.Entry<Integer, List<SupplierOrder>> entry : ordersByComponent.entrySet()) {
            Integer componentId = entry.getKey();
            List<SupplierOrder> componentOrders = entry.getValue();
            List<SupplierShipment> componentShipments = supplierShipments.stream()
                    .filter(ss -> componentOrders.stream().map(SupplierOrder::getId).toList().contains(ss.getSupplierOrderId()))
                    .toList();

            ComponentPerformanceMetrics componentMetrics = computeComponentMetrics(componentId, componentPerformances, componentOrders, componentShipments);

            totalDeliveredOrders += componentMetrics.getTotalDeliveredOrders();
            totalDelays += componentMetrics.getTotalDelays();
            ratioOfOnTimeOrderDeliveries += componentMetrics.getRatioOfOnTimeDeliveries();
            ratioOfOnTimeShipmentDeliveries += componentMetrics.getRatioOfOnTimeShipments();
            averageShipmentsPerOrder += componentMetrics.getAverageShipmentsPerOrder();
            averageTimeToShipOrder += componentMetrics.getAverageTimeToShipOrder();
        }

        // Compute average metrics
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

        // Compute overall score
        timelinessScore = 1 - (totalDelays / totalDeliveredOrders) * 100;
        quantityPerTimeScore = 0; // Not implemented yet
        availabilityScore = (1 - (1 - ratioOfOnTimeShipmentDeliveries / totalDeliveredOrders)) * 100;
        qualityScore = 100; // Not implemented yet
        overallScore = (timelinessScore + quantityPerTimeScore + availabilityScore + qualityScore) / 4;

        report.setComponentPerformances(componentPerformances);
        report.setOverallScore(overallScore);
        report.setTimelinessScore(timelinessScore);
        report.setQuantityPerTimeScore(quantityPerTimeScore);
        report.setAvailabilityScore(availabilityScore);
        report.setQualityScore(qualityScore);

        return report;
    }

    private ComponentPerformanceMetrics computeComponentMetrics(Integer componentId,
                                         Map<Integer, ComponentDeliveryPerformance> componentPerformances,
                                         List<SupplierOrder> componentOrders,
                                         List<SupplierShipment> componentShipments) {
        int totalDeliveredComponentOrders = 0;
        float totalDelaysComponent = 0;
        float ratioOfOnTimeOrderDeliveriesComponent = 0;
        float ratioOfOnTimeShipmentDeliveriesComponent = 0;
        float averageShipmentsPerOrderComponent = 0;
        float averageTimeToShipOrderComponent = 0;

        float totalDeliveredQuantity = 0;
        float averageDeliveredQuantity = 0;
        float averageOrderQuantity = 0;
        float averageShipmentQuantity = 0;
        float deliveredPerOrderedRatio = 0;

        LocalDateTime firstDeliveryDate = componentOrders.stream()
                .map(SupplierOrder::getDeliveryDate).filter(Objects::nonNull) // Filter out null delivery dates
                .min(LocalDateTime::compareTo).orElse(null);
        Map<Float, Float> deliveredQuantityOverTime = new HashMap<>();

        for (SupplierOrder supplierOrder : componentOrders) {
            // Compute delivery metrics
            if (supplierOrder.getDeliveryDate() == null) continue;
            totalDeliveredComponentOrders++;

            if (supplierOrder.getEstimatedDeliveryDate() != null) {
                Duration orderDelay = Duration.between(supplierOrder.getEstimatedDeliveryDate(), supplierOrder.getDeliveryDate());
                totalDelaysComponent += orderDelay.toDays();
                if (totalDelaysComponent <= 0) {
                    ratioOfOnTimeOrderDeliveriesComponent++;
                }
            }

            if (supplierOrder.getOrderDate() != null) {
                Duration shipDuration = Duration.between(supplierOrder.getOrderDate(), supplierOrder.getDeliveryDate());
                averageTimeToShipOrderComponent += shipDuration.toDays();
            }

            List<SupplierShipment> correspondingShipments = componentShipments.stream()
                    .filter(ss -> ss.getSupplierOrderId().equals(supplierOrder.getId()))
                    .toList();

            averageShipmentsPerOrderComponent += correspondingShipments.size();

            // Compute quantity metrics
            float orderDeliveredQuantity = supplierOrder.getDeliveredQuantity() != null ? supplierOrder.getDeliveredQuantity() : 0;
            totalDeliveredQuantity += orderDeliveredQuantity;
            averageDeliveredQuantity += orderDeliveredQuantity;
            averageOrderQuantity += supplierOrder.getQuantity();
            averageShipmentQuantity += correspondingShipments.stream().map(SupplierShipment::getQuantity).reduce(0.0f, Float::sum);
            deliveredPerOrderedRatio += orderDeliveredQuantity;
            if (firstDeliveryDate == null) continue;
            Duration timeFromFirstDelivery = Duration.between(firstDeliveryDate, supplierOrder.getDeliveryDate());
            long days = timeFromFirstDelivery.toDays();
            deliveredQuantityOverTime.put((float) days, orderDeliveredQuantity);
        }

        // Get component performance
        ComponentDeliveryPerformance componentPerformance = new ComponentDeliveryPerformance();
        componentPerformance.setComponentId(componentId);
        componentPerformance.setComponentName(componentOrders.getFirst().getComponent().getName());
        componentPerformance.setTotalDeliveredOrders(totalDeliveredComponentOrders);
        componentPerformance.setTotalDeliveredQuantity(totalDeliveredQuantity);
        if (totalDeliveredComponentOrders > 0) {
            componentPerformance.setAverageDeliveredQuantity(averageDeliveredQuantity / totalDeliveredComponentOrders);
            componentPerformance.setAverageOrderQuantity(averageOrderQuantity / totalDeliveredComponentOrders);
            componentPerformance.setDeliveredPerOrderedRatio(deliveredPerOrderedRatio / totalDeliveredComponentOrders);
            componentPerformance.setAverageShipmentQuantity(averageShipmentQuantity / totalDeliveredComponentOrders); // Not good yet
            componentPerformance.setFirstDeliveryDate(firstDeliveryDate);
            componentPerformance.setDeliveredQuantityOverTime(deliveredQuantityOverTime);
            componentPerformances.put(componentId, componentPerformance);
        }

        ComponentPerformanceMetrics componentPerformanceMetrics = new ComponentPerformanceMetrics();
        componentPerformanceMetrics.setTotalDeliveredOrders(totalDeliveredComponentOrders);
        componentPerformanceMetrics.setTotalDelays(totalDelaysComponent);
        componentPerformanceMetrics.setRatioOfOnTimeDeliveries(ratioOfOnTimeOrderDeliveriesComponent);
        componentPerformanceMetrics.setRatioOfOnTimeShipments(ratioOfOnTimeShipmentDeliveriesComponent);
        componentPerformanceMetrics.setAverageShipmentsPerOrder(averageShipmentsPerOrderComponent);
        componentPerformanceMetrics.setAverageTimeToShipOrder(averageTimeToShipOrderComponent);

        return componentPerformanceMetrics;
    }
}
