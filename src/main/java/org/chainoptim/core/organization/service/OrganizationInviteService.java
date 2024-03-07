package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.organization.model.OrganizationInvite;

import java.util.List;

public interface OrganizationInviteService {
    OrganizationInvite createOrganizationInvite(CreateOrganizationInviteDTO createOrganizationInviteDTO);
    OrganizationInvite getOrganizationInviteById(Integer id);
    List<OrganizationInvite> getAllOrganizationInvites();
    OrganizationInvite updateOrganizationInvite(OrganizationInvite organizationInvite);
    void deleteOrganizationInvite(Integer id);
}
