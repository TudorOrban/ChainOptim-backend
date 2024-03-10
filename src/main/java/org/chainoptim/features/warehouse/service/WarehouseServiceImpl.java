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


    public Warehouse getWarehouseById(Integer warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + warehouseId + " not found."));
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
        Warehouse warehouse = warehouseRepository.findById(warehouseDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + warehouseDTO.getId() + " not found."));

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
