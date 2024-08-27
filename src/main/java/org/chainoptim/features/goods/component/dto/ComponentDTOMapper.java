package org.chainoptim.features.goods.component.dto;

import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.controller.UnitOfMeasurement;
import org.chainoptim.features.goods.component.model.Component;

public class ComponentDTOMapper {

    private ComponentDTOMapper() {}

    public static ComponentsSearchDTO convertComponentToComponentsSearchDTO(Component component) {
        ComponentsSearchDTO componentsSearchDTO = new ComponentsSearchDTO();
        componentsSearchDTO.setId(component.getId());
        componentsSearchDTO.setName(component.getName());
        return componentsSearchDTO;
    }

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
        component.setNewUnit(componentDTO.getNewUnit());

        return component;
    }

    public static Component setUpdateComponentDTOToComponent(Component component, UpdateComponentDTO componentDTO) {
        component.setName(componentDTO.getName());
        component.setDescription(componentDTO.getDescription());
        if (componentDTO.getUnitId() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            unitOfMeasurement.setId(componentDTO.getUnitId());
            component.setUnit(unitOfMeasurement);
        }
        component.setNewUnit(componentDTO.getNewUnit());

        return component;
    }
}
