package org.chainoptim.core.map.service;

import org.chainoptim.core.map.model.Facility;
import org.chainoptim.core.map.model.FacilityType;
import org.chainoptim.core.map.model.MapData;
import org.chainoptim.core.map.model.SupplyChainMap;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ClientRepository clientRepository;

    @Autowired
    public SupplyChainMapRefreshServiceImpl(FactoryRepository factoryRepository, WarehouseRepository warehouseRepository, SupplierRepository supplierRepository, ClientRepository clientRepository) {
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
    }

    public MapData refreshSupplyChainMap(Integer organizationId) {
        List<Facility> facilities = new ArrayList<>();

        facilities.addAll(processEntities(factoryRepository.findByOrganizationId(organizationId),
                Factory::getId, Factory::getName, Factory::getLocation, FacilityType.FACTORY));

        facilities.addAll(processEntities(warehouseRepository.findByOrganizationId(organizationId),
                Warehouse::getId, Warehouse::getName, Warehouse::getLocation, FacilityType.WAREHOUSE));

        facilities.addAll(processEntities(supplierRepository.findByOrganizationId(organizationId),
                Supplier::getId, Supplier::getName, Supplier::getLocation, FacilityType.SUPPLIER));

        facilities.addAll(processEntities(clientRepository.findByOrganizationId(organizationId),
                Client::getId, Client::getName, Client::getLocation, FacilityType.CLIENT));

        return new MapData(facilities);
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
}
