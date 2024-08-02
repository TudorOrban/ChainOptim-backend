package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.ComponentsSearchDTO;
import org.chainoptim.features.productpipeline.dto.CreateComponentDTO;
import org.chainoptim.features.productpipeline.dto.UpdateComponentDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ComponentService {
    List<Component> getComponentsByOrganizationId(Integer organizationId);
    List<ComponentsSearchDTO> getComponentsByOrganizationIdSmall(Integer organizationId);
    PaginatedResults<ComponentsSearchDTO> getComponentsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    Component getComponentById(Integer id);
    Component createComponent(CreateComponentDTO componentDTO);
    Component updateComponent(UpdateComponentDTO componentDTO);
    void deleteComponent(Integer id);
}
