package org.chainoptim.shared.util;

import org.chainoptim.core.tenant.customrole.model.CustomRole;
import org.chainoptim.core.tenant.customrole.model.FeaturePermissions;
import org.chainoptim.core.tenant.customrole.model.Permissions;
import org.chainoptim.core.tenant.customrole.repository.CustomRoleRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

@Service
public class FeaturePermissionTransitionerService {

    private final CustomRoleRepository customRoleRepository;

    public FeaturePermissionTransitionerService(CustomRoleRepository customRoleRepository) {
        this.customRoleRepository = customRoleRepository;
    }

    private static final int REFRESH_INTERVAL = 60000 * 60 * 24; // 24 hours


    @Async
//    @Scheduled(fixedDelay = REFRESH_INTERVAL)
    public void transitionToNewPermissionsDataStructure() {
        List<CustomRole> customRoles = customRoleRepository.findAll();

        for (CustomRole customRole : customRoles) {
            Permissions permissions = transitionPermissions(customRole.getPermissions());
            customRole.setPermissions(permissions);
        }

        customRoleRepository.saveAll(customRoles);
    }

    private Permissions transitionPermissions(Permissions permissions) {
        // Initialize feature map
        EnumMap<Feature, FeaturePermissions> featurePermissions = new EnumMap<>(Feature.class);
        for (Feature feature : Feature.values()) {
            featurePermissions.put(feature, new FeaturePermissions());
        }

        featurePermissions.put(Feature.CUSTOM_ROLE, permissions.getOrganization());
        featurePermissions.put(Feature.PRODUCT, permissions.getProducts());
        featurePermissions.put(Feature.FACTORY, permissions.getFactories());
        featurePermissions.put(Feature.WAREHOUSE, permissions.getWarehouses());
        featurePermissions.put(Feature.SUPPLIER, permissions.getSuppliers());
        featurePermissions.put(Feature.CLIENT, permissions.getClients());

        permissions.setFeaturePermissions(featurePermissions);

        return permissions;
    }
}
