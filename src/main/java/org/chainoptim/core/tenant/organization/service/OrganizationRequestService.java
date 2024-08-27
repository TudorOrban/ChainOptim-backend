package org.chainoptim.core.tenant.organization.service;

import org.chainoptim.core.tenant.organization.model.OrganizationRequest;

import java.util.List;

public interface OrganizationRequestService {

    OrganizationRequest createOrganizationRequest(OrganizationRequest organizationRequest);
    OrganizationRequest getOrganizationRequestById(Integer id);
    List<OrganizationRequest> getAllOrganizationRequests();
    OrganizationRequest updateOrganizationRequest(OrganizationRequest organizationRequest);
    void deleteOrganizationRequest(Integer id);
}
