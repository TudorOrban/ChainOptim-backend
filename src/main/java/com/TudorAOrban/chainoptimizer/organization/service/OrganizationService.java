package com.TudorAOrban.chainoptimizer.organization.service;

import com.TudorAOrban.chainoptimizer.dto.OrganizationDTO;
import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import com.TudorAOrban.chainoptimizer.organization.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Transactional
    public OrganizationDTO getOrganizationById(Integer id, boolean includeUsers) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return organizationMapper.mapOrganizationToDTO(organization, includeUsers);
        }
        return null;
    }



    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization updateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }
}
