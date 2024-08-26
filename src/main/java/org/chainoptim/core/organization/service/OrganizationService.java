package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.organization.dto.UpdateOrganizationDTO;
import org.chainoptim.core.organization.model.Organization;

import java.util.List;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(Integer id, boolean includeUsers);
    Organization createOrganization(CreateOrganizationDTO createOrganizationDTO);
    Organization updateOrganization(UpdateOrganizationDTO organizationDTO);
    void deleteOrganization(Integer id);
    void unsubscribeOrganization(Integer id);
}
