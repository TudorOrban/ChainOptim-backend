package org.chainoptim.core.map.service;

import org.chainoptim.core.map.model.*;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.supplier.repository.SupplierShipmentRepository;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

@Service
public class SupplyChainMapRefreshServiceImpl implements SupplyChainMapRefreshService {

    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierShipmentRepository supplierShipmentRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public SupplyChainMapRefreshServiceImpl(
            FactoryRepository factoryRepository,
            WarehouseRepository warehouseRepository,
            SupplierRepository supplierRepository,
            SupplierShipmentRepository supplierShipmentRepository,
            ClientRepository clientRepository) {
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.supplierShipmentRepository = supplierShipmentRepository;
        this.clientRepository = clientRepository;
    }

    public MapData refreshSupplyChainMap(Integer organizationId) {
        List<Facility> facilities = new ArrayList<>();
        List<TransportRoute> transportRoutes = new ArrayList<>();

        // Facilities
        facilities.addAll(processEntities(factoryRepository.findByOrganizationId(organizationId),
                Factory::getId, Factory::getName, Factory::getLocation, FacilityType.FACTORY));

        facilities.addAll(processEntities(warehouseRepository.findByOrganizationId(organizationId),
                Warehouse::getId, Warehouse::getName, Warehouse::getLocation, FacilityType.WAREHOUSE));

        facilities.addAll(processEntities(supplierRepository.findByOrganizationId(organizationId),
                Supplier::getId, Supplier::getName, Supplier::getLocation, FacilityType.SUPPLIER));

        facilities.addAll(processEntities(clientRepository.findByOrganizationId(organizationId),
                Client::getId, Client::getName, Client::getLocation, FacilityType.CLIENT));

        // Transport routes
        List<SupplierShipment> supplierShipments = supplierShipmentRepository.findByOrganizationId(organizationId);

        for (SupplierShipment supplierShipment : supplierShipments) {
            TransportRoute transportRoute = transformToRoute(supplierShipment);

            transportRoutes.add(transportRoute);
        }

        return new MapData(facilities, transportRoutes);
    }

    private <T> List<Facility> processEntities(List<T> entities,
                                               ToIntFunction<T> idExtractor,
                                               Function<T, String> nameExtractor,
                                               Function<T, Location> locationExtractor,
                                               FacilityType type) {
        return entities.stream()
                .filter(e -> locationExtractor.apply(e) != null && locationExtractor.apply(e).getLatitude() != null && locationExtractor.apply(e).getLongitude() != null)
                .map(e -> new Facility(idExtractor.applyAsInt(e),
                        nameExtractor.apply(e),
                        type,
                        locationExtractor.apply(e).getLatitude(),
                        locationExtractor.apply(e).getLongitude()))
                .toList();
    }

    private TransportRoute transformToRoute(SupplierShipment supplierShipment) {
        TransportRoute transportRoute = new TransportRoute();
        transportRoute.setEntityId(supplierShipment.getId());
        transportRoute.setEntityType(Feature.SUPPLIER_SHIPMENT);
        Location srcLocation = supplierShipment.getSourceLocation();
        if (srcLocation != null) {
            transportRoute.setSrcLocation(Pair.of(srcLocation.getLatitude(), srcLocation.getLongitude()));
        }

        Location destLocation = supplierShipment.getDestinationLocation();
        if (destLocation != null) {
            transportRoute.setDestLocation(Pair.of(destLocation.getLatitude(), destLocation.getLongitude()));
        }
        if (supplierShipment.getDestFactoryId() != null) {
            transportRoute.setDestFacilityId(supplierShipment.getDestFactoryId());
            transportRoute.setDestFacilityType(FacilityType.FACTORY);
        } else if (supplierShipment.getDestWarehouseId() != null) {
            transportRoute.setDestFacilityId(supplierShipment.getDestWarehouseId());
            transportRoute.setDestFacilityType(FacilityType.WAREHOUSE);
        }

        if (supplierShipment.getCurrentLocationLatitude() != null && supplierShipment.getCurrentLocationLongitude() != null) {
            transportRoute.setLiveLocation(Pair.of(supplierShipment.getCurrentLocationLatitude(), supplierShipment.getCurrentLocationLongitude()));
        }

        transportRoute.setTransportType(supplierShipment.getTransportType());
        transportRoute.setShipmentStatus(supplierShipment.getStatus());

        transportRoute.setDepartureDateTime(supplierShipment.getDepartureDate());
        transportRoute.setEstimatedArrivalDateTime(supplierShipment.getEstimatedArrivalDate());
        transportRoute.setArrivalDateTime(supplierShipment.getArrivalDate());

        return transportRoute;
    }
}
