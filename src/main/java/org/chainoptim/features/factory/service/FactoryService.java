package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.product.dto.ProductsSearchDTO;

import java.util.List;
import java.util.Optional;

public interface FactoryService {
    List<Factory> getAllFactories();
    Optional<Factory> getFactoryById(Integer id);
    List<Factory> getFactoriesByOrganizationId(Integer organizationId);
    List<FactoriesSearchDTO> getFactoriesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
}
