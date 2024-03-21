package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateComponentDTO;
import org.chainoptim.features.productpipeline.dto.UpdateComponentDTO;
import org.chainoptim.features.productpipeline.model.Component;

import java.util.List;

public interface ComponentService {
    List<Component> getComponentsByOrganizationId(Integer organizationId);
    Component createComponent(CreateComponentDTO componentDTO);
    Component updateComponent(UpdateComponentDTO componentDTO);
    void deleteComponent(Integer id);
}
