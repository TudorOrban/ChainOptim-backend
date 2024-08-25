package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.organization.dto.CreateOrganizationUserDTO;
import org.chainoptim.core.organization.dto.UpdateOrganizationDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.core.user.service.UserService;
import org.chainoptim.core.user.service.UserWriteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserWriteService userWriteService;
    @Mock
    private OrganizationInviteService organizationInviteService;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Test
    void testCreateOrganization() {
        // Arrange
        CreateOrganizationDTO createOrganizationDTO = new CreateOrganizationDTO();
        createOrganizationDTO.setName("Test Organization");
        createOrganizationDTO.setAddress("Test Address");
        createOrganizationDTO.setContactInfo("Test Contact Info");
        createOrganizationDTO.setPlanTier(SubscriptionPlanTier.BASIC);
        User creator = Mockito.mock(User.class);
        Mockito.when(creator.getId()).thenReturn("Test Creator ID");
        createOrganizationDTO.setCreatorId(creator.getId());
        createOrganizationDTO.setExistingUserIds(Set.of("Test Existing User ID"));

        Set<CreateOrganizationUserDTO> createdUsers = new HashSet<>();
        CreateOrganizationUserDTO createOrganizationUserDTO = new CreateOrganizationUserDTO();
        createOrganizationUserDTO.setUsername("Test Username");
        createOrganizationUserDTO.setEmail("Test Email");
        createOrganizationUserDTO.setRole(User.Role.MEMBER);

        createdUsers.add(createOrganizationUserDTO);
        createOrganizationDTO.setCreatedUsers(createdUsers);

        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setId(1);
            return org;
        });
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(creator).setOrganization(any(Organization.class));
        when(userService.getUserById(any(String.class))).thenReturn(creator);
        when(userWriteService.registerNewOrganizationUser(any(), any(), any(), any())).thenAnswer(invocation -> {
            User newUser = new User();
            newUser.setUsername(invocation.getArgument(0));
            newUser.setEmail(invocation.getArgument(1));
            newUser.setRole(invocation.getArgument(3));
            return newUser;
        });
        when(organizationInviteService.createOrganizationInvite(any(CreateOrganizationInviteDTO.class))).thenReturn(null);

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> organizationIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<User.Role> roleCaptor = ArgumentCaptor.forClass(User.Role.class);

        // Act
        Organization createdOrganization = organizationService.createOrganization(createOrganizationDTO);

        // Assert
        assertNotNull(createdOrganization);
        assertEquals(createOrganizationDTO.getName(), createdOrganization.getName());
        assertEquals(createOrganizationDTO.getAddress(), createdOrganization.getAddress());
        assertEquals(createOrganizationDTO.getContactInfo(), createdOrganization.getContactInfo());
        assertEquals(createOrganizationDTO.getPlanTier(), createdOrganization.getSubscriptionPlanTier());

        // Assert user registrations
        verify(userWriteService).registerNewOrganizationUser(usernameCaptor.capture(), emailCaptor.capture(), organizationIdCaptor.capture(), roleCaptor.capture());
        assertEquals("Test Username", usernameCaptor.getValue());
        assertEquals("Test Email", emailCaptor.getValue());
        assertEquals(createdOrganization.getId(), organizationIdCaptor.getValue());
        assertEquals(User.Role.MEMBER, roleCaptor.getValue());

        // Assert existing user handling (invites)
        ArgumentCaptor<CreateOrganizationInviteDTO> inviteCaptor = ArgumentCaptor.forClass(CreateOrganizationInviteDTO.class);
        verify(organizationInviteService, times(createOrganizationDTO.getExistingUserIds().size())).createOrganizationInvite(inviteCaptor.capture());
        CreateOrganizationInviteDTO capturedInvite = inviteCaptor.getValue();
        assertEquals(createdOrganization.getId(), capturedInvite.getOrganizationId());
        assertEquals("Test Creator ID", capturedInvite.getInviterId());
    }

    @Test
    void testUpdateOrganization() {
        // Arrange
        UpdateOrganizationDTO organizationDTO = new UpdateOrganizationDTO();
        organizationDTO.setId(1);
        organizationDTO.setName("Test Organization");
        organizationDTO.setAddress("Test Address");
        organizationDTO.setContactInfo("Test Contact Info");

        Organization existingOrganization = new Organization();
        existingOrganization.setId(1);
        existingOrganization.setName("Existing Organization");
        existingOrganization.setAddress("Existing Address");
        existingOrganization.setContactInfo("Existing Contact Info");

        when(organizationRepository.findById(1)).thenReturn(java.util.Optional.of(existingOrganization));
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Organization updatedOrganization = organizationService.updateOrganization(organizationDTO);

        // Assert
        assertNotNull(updatedOrganization);
        assertEquals(organizationDTO.getName(), updatedOrganization.getName());
        assertEquals(organizationDTO.getAddress(), updatedOrganization.getAddress());
        assertEquals(organizationDTO.getContactInfo(), updatedOrganization.getContactInfo());
    }

    @Test
    void testDeleteOrganization() {
        // Arrange
        doNothing().when(organizationRepository).deleteById(any(Integer.class));

        // Act
        organizationService.deleteOrganization(1);

        // Assert
        verify(organizationRepository, times(1)).deleteById(any(Integer.class));
    }

}
