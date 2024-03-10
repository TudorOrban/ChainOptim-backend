package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.features.productpipeline.model.RawMaterial;
import org.chainoptim.features.productpipeline.service.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @Autowired
    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping("/organizations/{organizationId}")
    public List<RawMaterial> getRawMaterialsByOrganization(@PathVariable Integer organizationId) {
        return rawMaterialService.getRawMaterialsByOrganization(organizationId);
    }
}
