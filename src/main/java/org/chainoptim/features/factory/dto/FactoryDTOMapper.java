package org.chainoptim.features.factory.dto;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.commonfeatures.location.model.Location;

public class FactoryDTOMapper {

    private FactoryDTOMapper() {}

    public static FactoriesSearchDTO convertToFactoriesSearchDTO(Factory factory) {
        FactoriesSearchDTO dto = new FactoriesSearchDTO();
        dto.setId(factory.getId());
        dto.setName(factory.getName());
        dto.setCreatedAt(factory.getCreatedAt());
        dto.setUpdatedAt(factory.getUpdatedAt());
        dto.setLocation(factory.getLocation());
        return dto;
    }

    public static Factory convertCreateFactoryDTOToFactory(CreateFactoryDTO factoryDTO) {
        Factory factory = new Factory();
        factory.setName(factoryDTO.getName());
        factory.setOrganizationId(factoryDTO.getOrganizationId());
        Location location = new Location();
        location.setId(factoryDTO.getLocationId());
        factory.setLocation(location);

        return factory;
    }

    public static FactoryInventoryItem convertCreateFactoryItemDTOToFactoryItem(CreateFactoryInventoryItemDTO itemDTO) {
        FactoryInventoryItem item = new FactoryInventoryItem();
        item.setFactoryId(itemDTO.getFactoryId());
        if (itemDTO.getComponentId() != null) {
            Component component = new Component();
            component.setId(itemDTO.getComponentId());
            item.setComponent(component);
        }
        if (itemDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(itemDTO.getProductId());
            item.setProduct(product);
        }
        item.setQuantity(itemDTO.getQuantity());
        item.setMinimumRequiredQuantity(itemDTO.getMinimumRequiredQuantity());

        return item;
    }


}
