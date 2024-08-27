package org.chainoptim.features.supply.dto;

import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.model.SupplierOrder;
import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.shared.commonfeatures.location.model.Location;

public class SupplierDTOMapper {

    private SupplierDTOMapper() {}

    public static SuppliersSearchDTO convertToSuppliersSearchDTO(Supplier supplier) {
        SuppliersSearchDTO dto = new SuppliersSearchDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setCreatedAt(supplier.getCreatedAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());
        dto.setLocation(supplier.getLocation());
        dto.setOverallScore(supplier.getOverallScore());
        dto.setTimelinessScore(supplier.getTimelinessScore());
        dto.setAvailabilityScore(supplier.getAvailabilityScore());
        dto.setQualityScore(supplier.getQualityScore());
        return dto;
    }

    public static Supplier convertCreateSupplierDTOToSupplier(CreateSupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setOrganizationId(supplierDTO.getOrganizationId());
        if (supplierDTO.getLocationId() != null) {
            Location location = new Location();
            location.setId(supplierDTO.getLocationId());
            supplier.setLocation(location);
        }

        return supplier;
    }

    public static SupplierOrder mapCreateDtoToSupplierOrder(CreateSupplierOrderDTO order) {
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setOrganizationId(order.getOrganizationId());
        supplierOrder.setSupplierId(order.getSupplierId());
        supplierOrder.setQuantity(order.getQuantity());
        supplierOrder.setDeliveredQuantity(order.getDeliveredQuantity());
        supplierOrder.setOrderDate(order.getOrderDate());
        supplierOrder.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        supplierOrder.setDeliveryDate(order.getDeliveryDate());
        supplierOrder.setStatus(order.getStatus());
        supplierOrder.setCompanyId(order.getCompanyId());

        return supplierOrder;
    }

    public static void setUpdateSupplierOrderDTOToUpdateOrder(SupplierOrder supplierOrder, UpdateSupplierOrderDTO orderDTO) {
        supplierOrder.setQuantity(orderDTO.getQuantity());
        supplierOrder.setDeliveredQuantity(orderDTO.getDeliveredQuantity());
        supplierOrder.setOrderDate(orderDTO.getOrderDate());
        supplierOrder.setEstimatedDeliveryDate(orderDTO.getEstimatedDeliveryDate());
        supplierOrder.setDeliveryDate(orderDTO.getDeliveryDate());
        supplierOrder.setStatus(orderDTO.getStatus());
        supplierOrder.setCompanyId(orderDTO.getCompanyId());
    }

    public static SupplierShipment mapCreateSupplierShipmentDTOTOShipment(CreateSupplierShipmentDTO shipmentDTO) {
        SupplierShipment shipment = new SupplierShipment();
        shipment.setOrganizationId(shipmentDTO.getOrganizationId());
        shipment.setSupplierId(shipmentDTO.getSupplierId());
        shipment.setSupplierOrderId(shipmentDTO.getSupplierOrderId());
        shipment.setQuantity(shipmentDTO.getQuantity());
        shipment.setShipmentStartingDate(shipmentDTO.getShipmentStartingDate());
        shipment.setEstimatedArrivalDate(shipmentDTO.getEstimatedArrivalDate());
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setStatus(shipmentDTO.getStatus());
        shipment.setCurrentLocationLatitude(shipmentDTO.getCurrentLocationLatitude());
        shipment.setCurrentLocationLongitude(shipmentDTO.getCurrentLocationLongitude());

        return shipment;
    }

    public static void setUpdateSupplierShipmentDTOToSupplierShipment(SupplierShipment shipment, UpdateSupplierShipmentDTO shipmentDTO) {
        shipment.setQuantity(shipmentDTO.getQuantity());
        shipment.setShipmentStartingDate(shipmentDTO.getShipmentStartingDate());
        shipment.setEstimatedArrivalDate(shipmentDTO.getEstimatedArrivalDate());
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setStatus(shipmentDTO.getStatus());
        shipment.setCurrentLocationLatitude(shipmentDTO.getCurrentLocationLatitude());
        shipment.setCurrentLocationLongitude(shipmentDTO.getCurrentLocationLongitude());
    }
}
