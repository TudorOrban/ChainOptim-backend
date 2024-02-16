package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.Component;

import java.util.List;

public interface ComponentService {
    public List<Component> getComponentsByOrganization(Integer organizationId);
}
