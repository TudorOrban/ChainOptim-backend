package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.organization.model.OrganizationInvite;

import java.util.List;

public interface OrganizationInviteService {
    public OrganizationInvite createOrganizationInvite(CreateOrganizationInviteDTO createOrganizationInviteDTO);
    public OrganizationInvite getOrganizationInviteById(Integer id);
    public List<OrganizationInvite> getAllOrganizationInvites();
    public OrganizationInvite updateOrganizationInvite(OrganizationInvite organizationInvite);
    public void deleteOrganizationInvite(Integer id);
}
