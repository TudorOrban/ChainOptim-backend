package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.organization.model.Organization;

import java.util.List;

public interface OrganizationService {
    public Organization createOrganization(CreateOrganizationDTO createOrganizationDTO);
    public OrganizationDTO getOrganizationById(Integer id, boolean includeUsers);
    public List<Organization> getAllOrganizations();
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Integer id);
}
