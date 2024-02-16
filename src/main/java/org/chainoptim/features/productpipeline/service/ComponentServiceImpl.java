package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    @Autowired
    public ComponentServiceImpl(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getComponentsByOrganization(Integer organizationId) {
        return componentRepository.findByOrganizationId(organizationId);
    }
}
