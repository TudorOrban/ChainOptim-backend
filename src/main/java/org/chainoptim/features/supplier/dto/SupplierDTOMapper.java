package org.chainoptim.features.supplier.dto;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierShipment;
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
        Component component = new Component();
        component.setId(order.getComponentId());
        supplierOrder.setComponent(component);
        supplierOrder.setQuantity(order.getQuantity());
        supplierOrder.setOrderDate(order.getOrderDate());
        supplierOrder.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        supplierOrder.setDeliveryDate(order.getDeliveryDate());
        supplierOrder.setStatus(order.getStatus());
        supplierOrder.setCompanyId(order.getCompanyId());

        return supplierOrder;
    }

    public static void setUpdateSupplierOrderDTOToClientOrder(SupplierOrder supplierOrder, UpdateSupplierOrderDTO orderDTO) {
        supplierOrder.setQuantity(orderDTO.getQuantity());
        supplierOrder.setOrderDate(orderDTO.getOrderDate());
        supplierOrder.setEstimatedDeliveryDate(orderDTO.getEstimatedDeliveryDate());
        supplierOrder.setDeliveryDate(orderDTO.getDeliveryDate());
        supplierOrder.setStatus(orderDTO.getStatus());
        Component component = new Component();
        component.setId(orderDTO.getComponentId());
        supplierOrder.setComponent(component);
        supplierOrder.setCompanyId(orderDTO.getCompanyId());
    }

    public static SupplierShipment mapCreateSupplierShipmentDTOTOShipment(CreateSupplierShipmentDTO shipmentDTO) {
        SupplierShipment shipment = new SupplierShipment();
        shipment.setSupplierOrderId(shipmentDTO.getSupplierOrderId());
        shipment.setQuantity(shipmentDTO.getQuantity());
        shipment.setShipmentStartingDate(shipmentDTO.getShipmentStartingDate());
        shipment.setEstimatedArrivalDate(shipmentDTO.getEstimatedArrivalDate());
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setStatus(shipmentDTO.getStatus());
        Location sourceLocation = new Location();
        sourceLocation.setId(shipmentDTO.getSourceLocationId());
        shipment.setSourceLocation(sourceLocation);
        Location destinationLocation = new Location();
        destinationLocation.setId(shipmentDTO.getDestinationLocationId());
        shipment.setDestinationLocation(destinationLocation);
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
        Location sourceLocation = new Location();
        sourceLocation.setId(shipmentDTO.getSourceLocationId());
        shipment.setSourceLocation(sourceLocation);
        Location destinationLocation = new Location();
        destinationLocation.setId(shipmentDTO.getDestinationLocationId());
        shipment.setDestinationLocation(destinationLocation);
        shipment.setCurrentLocationLatitude(shipmentDTO.getCurrentLocationLatitude());
        shipment.setCurrentLocationLongitude(shipmentDTO.getCurrentLocationLongitude());
    }
}
