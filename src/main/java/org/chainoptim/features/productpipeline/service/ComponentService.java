package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public List<Component> getComponentsByOrganization(Integer organizationId) {
        return componentRepository.findByOrganizationId(organizationId);
    }
}
