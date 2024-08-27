package org.chainoptim.core.tenant.controller;

import org.chainoptim.core.tenant.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.tenant.organization.dto.CreateOrganizationUserDTO;
import org.chainoptim.core.tenant.organization.dto.OrganizationDTO;
import org.chainoptim.core.tenant.organization.dto.UpdateOrganizationDTO;
import org.chainoptim.core.tenant.organization.model.Organization;
import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.tenant.organization.repository.OrganizationRepository;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.chainoptim.testutil.TestDataSeederService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    Integer organizationId;
    String jwtToken;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();
    }

    @Test
    void testGetOrganization() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/organizations/" + organizationId + "?includeUsers=true";
        String invalidJWTToken = "Invalid";

        // Act and assert error status for invalid credentials
        mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + invalidJWTToken))
                .andExpect(status().is(403))
                .andReturn();

        // Act
        MvcResult mvcResult = mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        // Extract and deserialize response
        String response = mvcResult.getResponse().getContentAsString();
        OrganizationDTO organizationDTO = objectMapper.readValue(response, OrganizationDTO.class);

        // Assert
        assertNotNull(organizationDTO);
        assertEquals(organizationId, organizationDTO.getId());
        assertEquals("Test Org", organizationDTO.getName());
        assertEquals(SubscriptionPlanTier.PROFESSIONAL, organizationDTO.getSubscriptionPlanTier());
        assertEquals(1, organizationDTO.getUsers().size());
    }

    @Test
    void testCreateOrganization() throws Exception {
        // Arrange
        // - Create user that will create the organization
        User newUser = new User();
        newUser.setUsername("Creator User");
        newUser.setEmail("testemail@gmail.com");

        newUser = userRepository.save(newUser);

        // - Set up organization DTO
        CreateOrganizationDTO createOrganizationDTO = new CreateOrganizationDTO();
        createOrganizationDTO.setName("New Organization");
        createOrganizationDTO.setAddress("New Address");
        createOrganizationDTO.setContactInfo("New Contact Info");
        createOrganizationDTO.setPlanTier(SubscriptionPlanTier.BASIC);
        createOrganizationDTO.setCreatorId(newUser.getId());

        Set<CreateOrganizationUserDTO> createdUsers = new HashSet<>();
        CreateOrganizationUserDTO createOrganizationUserDTO = new CreateOrganizationUserDTO();
        createOrganizationUserDTO.setUsername("Test Username");
        createOrganizationUserDTO.setEmail("Test Email");
        createOrganizationUserDTO.setRole(User.Role.MEMBER);
        createdUsers.add(createOrganizationUserDTO);
        createOrganizationDTO.setCreatedUsers(createdUsers);

        String organizationDTOJson = objectMapper.writeValueAsString(createOrganizationDTO);

        // Act
        MvcResult result = mockMvc.perform(post("http://localhost:8080/api/v1/organizations/create")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(organizationDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        Organization createdOrganizationResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Organization.class);

        // Assert
        Optional<Organization> createdOrganizationOptional = organizationRepository.findByIdWithUsers(createdOrganizationResponse.getId());

        assertTrue(createdOrganizationOptional.isPresent());
        Organization createdOrganization = createdOrganizationOptional.get();

        assertEquals("New Organization", createdOrganization.getName());
        assertEquals("New Address", createdOrganization.getAddress());
        assertEquals("New Contact Info", createdOrganization.getContactInfo());
        assertEquals(SubscriptionPlanTier.BASIC, createdOrganization.getSubscriptionPlanTier());

        assertEquals(2, createdOrganization.getUsers().size()); // Creator and created user
        List<User> creators = createdOrganization.getUsers().stream().filter(user -> user.getUsername().equals("Creator User")).toList();
        assertEquals(1, creators.size());
        assertEquals(User.Role.ADMIN, creators.getFirst().getRole());
    }

    @Test
    void testUpdateOrganization() throws Exception {
        UpdateOrganizationDTO updateOrganizationDTO = new UpdateOrganizationDTO();
        updateOrganizationDTO.setId(organizationId);
        updateOrganizationDTO.setName("Updated Name");
        updateOrganizationDTO.setAddress("Updated Address");
        updateOrganizationDTO.setContactInfo("Updated Contact Info");

        String url = "http://localhost:8080/api/v1/organizations/update";
        String invalidJWTToken = "Invalid";

        // Act and assert error status for invalid credentials
        mockMvc.perform(put(url)
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrganizationDTO)))
                        .andExpect(status().is(403))
                        .andReturn();

        Optional<Organization> organizationOptional = organizationRepository.findByName("Updated Name");
        if (organizationOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token");
        }

        // Act
        mockMvc.perform(put(url)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrganizationDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Optional<Organization> updatedOrganizationOptional = organizationRepository.findByName("Updated Name");
        if (updatedOrganizationOptional.isEmpty()) {
            fail("Updated organization has not been found");
        }
        Organization updatedOrganization = updatedOrganizationOptional.get();
        assertNotNull(updatedOrganization);
        assertEquals(updateOrganizationDTO.getName(), updatedOrganization.getName());
        assertEquals(updateOrganizationDTO.getAddress(), updatedOrganization.getAddress());
        assertEquals(updateOrganizationDTO.getContactInfo(), updatedOrganization.getContactInfo());
    }

    @Test
    void testDeleteOrganization() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/organizations/" + organizationId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(403))
                        .andReturn();

        // Assert
        Optional<Organization> invalidDeletedOrganizationOptional = organizationRepository.findById(organizationId);
        if (invalidDeletedOrganizationOptional.isEmpty()) {
            fail("Failed to prevent deletion with invalid JWT token.");
        }

        // Act
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + jwtToken))
                        .andExpect(status().isOk());

        // Assert
        Optional<Organization> deletedOrganizationOptional = organizationRepository.findById(organizationId);
        assertTrue(deletedOrganizationOptional.isEmpty());
    }

}
