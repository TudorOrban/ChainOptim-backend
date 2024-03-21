package org.chainoptim.features.productpipeline.dto;

import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.productpipeline.model.Component;

public class ComponentDTOMapper {

    private ComponentDTOMapper() {}

    public static Component convertCreateComponentDTOToComponent(CreateComponentDTO componentDTO) {
        Component component = new Component();
        component.setOrganizationId(componentDTO.getOrganizationId());
        component.setName(componentDTO.getName());
        component.setDescription(componentDTO.getDescription());
        if (componentDTO.getUnitId() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            unitOfMeasurement.setId(componentDTO.getUnitId());
            component.setUnit(unitOfMeasurement);
        }

        return component;
    }
}
