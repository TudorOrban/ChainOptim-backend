package org.chainoptim.core.tenant.organization.service;

import org.chainoptim.core.tenant.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.tenant.organization.dto.OrganizationDTO;
import org.chainoptim.core.tenant.organization.dto.UpdateOrganizationDTO;
import org.chainoptim.core.tenant.organization.model.Organization;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(Integer id, boolean includeUsers);
    Organization createOrganization(CreateOrganizationDTO createOrganizationDTO);
    Organization updateOrganization(UpdateOrganizationDTO organizationDTO);
    void deleteOrganization(Integer id);
    void unsubscribeOrganization(Integer id);
}
