package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.model.OrganizationRequest;

import java.util.List;

public interface OrganizationRequestService {

    public OrganizationRequest createOrganizationRequest(OrganizationRequest organizationRequest);
    public OrganizationRequest getOrganizationRequestById(Integer id);
    public List<OrganizationRequest> getAllOrganizationRequests();
    public OrganizationRequest updateOrganizationRequest(OrganizationRequest organizationRequest);
    public void deleteOrganizationRequest(Integer id);
}
