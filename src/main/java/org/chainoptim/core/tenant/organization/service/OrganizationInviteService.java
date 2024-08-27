package org.chainoptim.core.tenant.organization.service;

import org.chainoptim.core.tenant.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.tenant.organization.model.OrganizationInvite;

import java.util.List;

public interface OrganizationInviteService {
    OrganizationInvite createOrganizationInvite(CreateOrganizationInviteDTO createOrganizationInviteDTO);
    OrganizationInvite getOrganizationInviteById(Integer id);
    List<OrganizationInvite> getAllOrganizationInvites();
    OrganizationInvite updateOrganizationInvite(OrganizationInvite organizationInvite);
    void deleteOrganizationInvite(Integer id);
}
