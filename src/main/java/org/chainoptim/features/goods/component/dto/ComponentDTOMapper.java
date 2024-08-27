package org.chainoptim.features.goods.component.dto;

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
        component.setUnit(componentDTO.getNewUnit());

        return component;
    }

    public static Component setUpdateComponentDTOToComponent(Component component, UpdateComponentDTO componentDTO) {
        component.setName(componentDTO.getName());
        component.setDescription(componentDTO.getDescription());
        component.setUnit(componentDTO.getNewUnit());

        return component;
    }
}
