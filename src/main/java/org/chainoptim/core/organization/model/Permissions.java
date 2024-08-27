package org.chainoptim.core.organization.model;

import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {

    private FeaturePermissions organization;
    private FeaturePermissions products;
    private FeaturePermissions factories;
    private FeaturePermissions warehouses;
    private FeaturePermissions suppliers;
    private FeaturePermissions clients;

    private Map<Feature, FeaturePermissions> featurePermissions;
}
