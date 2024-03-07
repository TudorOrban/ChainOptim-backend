package org.chainoptim.features.warehouse.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.WarehouseDTOMapper;
import org.chainoptim.features.warehouse.dto.WarehousesSearchDTO;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;

import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Integer id) {
        return warehouseRepository.findById(id);
    }

    public List<Warehouse> getWarehousesByOrganizationId(Integer organizationId) {
        return warehouseRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<WarehousesSearchDTO> getWarehousesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Warehouse> paginatedResults = warehouseRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
                paginatedResults.results.stream()
                        .map(WarehouseDTOMapper::convertToWarehousesSearchDTO)
                        .toList(),
                paginatedResults.totalCount
        );
    }

    public Warehouse createWarehouse(CreateWarehouseDTO warehouseDTO) {
        return warehouseRepository.save(WarehouseDTOMapper.convertCreateWarehouseDTOToWarehouse(warehouseDTO));
    }

    public Warehouse updateWarehouse(UpdateWarehouseDTO warehouseDTO) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseDTO.getId());
        if (warehouseOptional.isEmpty()) {
            throw new ResourceNotFoundException("The requested warehouse does not exist");
        }
        Warehouse warehouse = warehouseOptional.get();
        warehouse.setName(warehouseDTO.getName());

        warehouseRepository.save(warehouse);

        return warehouse;
    }

    public void deleteWarehouse(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        warehouseRepository.delete(warehouse);
    }
}
