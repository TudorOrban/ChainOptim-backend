package org.chainoptim.config.security;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.features.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    private final ProductRepository productRepository;

    @Autowired
    public SecurityServiceImpl(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    public boolean canAccessEntity(Long entityId, String entityType) {
        Optional<Integer> entityOrganizationId;

        switch (entityType) {
            case "Product":
                entityOrganizationId = productRepository.findOrganizationIdById(entityId);
                break;
            default:
                throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }

        return canAccessOrganizationEntity(entityOrganizationId);
    }

    public boolean canAccessOrganizationEntity(Optional<Integer> organizationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer currentOrganizationId = userDetails.getOrganizationId();

        return organizationId.map(id -> id.equals(currentOrganizationId)).orElse(false);
    }
}
