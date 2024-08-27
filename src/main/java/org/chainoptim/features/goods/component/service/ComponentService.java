package org.chainoptim.features.goods.component.service;

import org.chainoptim.features.goods.component.dto.ComponentsSearchDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.component.model.Component;
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
