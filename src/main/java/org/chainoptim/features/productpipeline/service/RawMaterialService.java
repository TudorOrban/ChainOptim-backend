package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.RawMaterial;
import org.chainoptim.features.productpipeline.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public List<RawMaterial> getRawMaterialsByOrganization(Integer organizationId) {
        return rawMaterialRepository.findByOrganizationId(organizationId);
    }
}
