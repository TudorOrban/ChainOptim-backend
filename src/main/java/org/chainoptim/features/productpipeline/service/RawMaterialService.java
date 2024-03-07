package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.RawMaterial;

import java.util.List;

public interface RawMaterialService {
    List<RawMaterial> getRawMaterialsByOrganization(Integer organizationId);
}
