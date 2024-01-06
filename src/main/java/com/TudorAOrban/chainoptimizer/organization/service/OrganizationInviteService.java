package com.TudorAOrban.chainoptimizer.organization.service;

import com.TudorAOrban.chainoptimizer.dto.CreateOrganizationInviteDTO;
import com.TudorAOrban.chainoptimizer.organization.model.OrganizationInvite;
import com.TudorAOrban.chainoptimizer.organization.repository.OrganizationInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrganizationInviteService {

    @Autowired
    private OrganizationInviteRepository organizationInviteRepository;

    public OrganizationInvite createOrganizationInvite(CreateOrganizationInviteDTO createOrganizationInviteDTO) {
        OrganizationInvite organizationInvite = new OrganizationInvite();
        organizationInvite.setOrganizationId(createOrganizationInviteDTO.getOrganizationId());
        organizationInvite.setInviterId(createOrganizationInviteDTO.getInviterId());
        organizationInvite.setInviteeId(createOrganizationInviteDTO.getInviteeId());
        organizationInvite.setStatus(OrganizationInvite.InviteStatus.PENDING);
        organizationInvite.setCreatedAt(LocalDateTime.now());
        return organizationInviteRepository.save(organizationInvite);
    }

    public OrganizationInvite getOrganizationInviteById(Integer id) {
        return organizationInviteRepository.findById(id).orElse(null);
    }

    public List<OrganizationInvite> getAllOrganizationInvites() {
        return organizationInviteRepository.findAll();
    }

    public OrganizationInvite updateOrganizationInvite(OrganizationInvite organizationInvite) {
        return organizationInviteRepository.save(organizationInvite);
    }

    public void deleteOrganizationInvite(Integer id) {
        organizationInviteRepository.deleteById(id);
    }



}
