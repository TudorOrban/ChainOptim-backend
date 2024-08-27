package org.chainoptim.core.overview.map.repository;

import org.chainoptim.core.overview.map.model.SupplyChainMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplyChainMapRepository extends JpaRepository<SupplyChainMap, Integer> {

    Optional<SupplyChainMap> findByOrganizationId(Integer organizationId);
}
