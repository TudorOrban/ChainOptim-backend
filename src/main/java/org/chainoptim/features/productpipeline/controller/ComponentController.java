package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @GetMapping("/organizations/{organizationId}")
    public List<Component> getComponentsByOrganization(@PathVariable Integer organizationId) {
        return componentService.getComponentsByOrganization(organizationId);
    }
}
