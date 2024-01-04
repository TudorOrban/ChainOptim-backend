package com.TudorAOrban.chainoptimizer.organization.service;

import com.TudorAOrban.chainoptimizer.organization.model.OrganizationRequest;
import com.TudorAOrban.chainoptimizer.organization.repository.OrganizationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationRequestService {

    @Autowired
    private OrganizationRequestRepository organizationRequestRepository;

    public OrganizationRequest createOrganizationRequest(OrganizationRequest organizationRequest) {
        return organizationRequestRepository.save(organizationRequest);
    }

    public OrganizationRequest getOrganizationRequestById(Integer id) {
        return organizationRequestRepository.findById(id).orElse(null);
    }

    public List<OrganizationRequest> getAllOrganizationRequests() {
        return organizationRequestRepository.findAll();
    }

    public OrganizationRequest updateOrganizationRequest(OrganizationRequest organizationRequest) {
        return organizationRequestRepository.save(organizationRequest);
    }

    public void deleteOrganizationRequest(Integer id) {
        organizationRequestRepository.deleteById(id);
    }
}
