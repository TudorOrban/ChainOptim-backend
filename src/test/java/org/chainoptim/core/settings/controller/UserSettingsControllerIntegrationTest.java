package org.chainoptim.core.settings.controller;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.settings.dto.DeleteUserSettingsDTO;
import org.chainoptim.core.settings.model.GeneralSettings;
import org.chainoptim.core.settings.model.NotificationSettings;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.core.user.jwt.JwtTokenProvider;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.settings.model.UserSettings;
import org.chainoptim.core.settings.repository.UserSettingsRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserSettingsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserSettingsRepository userSettingsRepository;
    @Autowired
    private EntitySanitizerService entitySanitizerService;
    @Autowired
    private SubscriptionPlanLimiterService planLimiterService;
    @Autowired
    private SupplyChainSnapshotRepository snapshotRepository;

    // Necessary seed data
    Organization organization;
    String userId;
    String jwtToken;
    Integer userSettingsId;

    @BeforeEach
    void setUp() {
        // Create organization
        Organization newOrganization = Organization.builder()
                .name("Test Org")
                .subscriptionPlanTier(Organization.SubscriptionPlanTier.PRO)
                .build();

        organization = organizationRepository.save(newOrganization);
        Integer organizationId = organization.getId();

        Pair<String, String> userCredentials = createNewUser();
        userId = userCredentials.getFirst();
        jwtToken = userCredentials.getSecond();

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        supplyChainSnapshot.setSnapshot(new Snapshot());
        snapshotRepository.save(supplyChainSnapshot);

        // Set up userSettings for update and delete tests
        createTestUserSettings();
    }

    void createTestUserSettings() {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(true);
        
        UserSettings userSettings1 = new UserSettings();
        userSettings1.setUserId(userId);
        userSettings1.setGeneralSettings(generalSettings);
        userSettings1.setNotificationSettings(notificationSettings);

        userSettings1 = userSettingsRepository.save(userSettings1);
        userSettingsId = userSettings1.getId();
    }

    @Test
    void testSearchUserSettings() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/user-settings/user/" + userId;
        String invalidJWTToken = "Invalid";

        // Act and assert error status for invalid credentials
        MvcResult invalidMvcResult = mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + invalidJWTToken))
                .andExpect(status().is(403))
                .andReturn();

        // Act
        MvcResult mvcResult = mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        // Extract and deserialize response
        String responseContent = mvcResult.getResponse().getContentAsString();
        UserSettings userSettings = objectMapper.readValue(
                responseContent, new TypeReference<UserSettings>() {});

        // Assert
        assertNotNull(userSettings);
        assertEquals(userId, userSettings.getUserId());
        assertEquals(GeneralSettings.InfoLevel.ADVANCED, userSettings.getGeneralSettings().getInfoLevel());
        assertTrue(userSettings.getNotificationSettings().isSupplierOrdersOn());
    }

    @Test
    void testCreateUserSettings() throws Exception {
        // Arrange
        Pair<String, String> userCredentials = createNewUser();
        String newUserId = userCredentials.getFirst();
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(true);
        CreateUserSettingsDTO userSettingsDTO = new CreateUserSettingsDTO(newUserId, generalSettings, notificationSettings);
        String userSettingsDTOJson = objectMapper.writeValueAsString(userSettingsDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/user-settings/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSettingsDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<UserSettings> invalidCreatedUserSettingsOptional = userSettingsRepository.findByUserId(newUserId);
        if (invalidCreatedUserSettingsOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/user-settings/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSettingsDTOJson));

        // Assert
        Optional<UserSettings> createdUserSettingsOptional = userSettingsRepository.findByUserId(newUserId);
        if (createdUserSettingsOptional.isEmpty()) {
            fail("Created userSettings has not been found");
        }
        UserSettings createdUserSettings = createdUserSettingsOptional.get();
        assertNotNull(createdUserSettings);
        assertEquals(newUserId, createdUserSettings.getUserId());
        assertEquals(GeneralSettings.InfoLevel.ADVANCED, createdUserSettings.getGeneralSettings().getInfoLevel());
        assertTrue(createdUserSettings.getNotificationSettings().isSupplierOrdersOn());
    }

    @Test
    void testUpdateUserSettings() throws Exception {
        // Arrange
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(false);
        UpdateUserSettingsDTO userSettingsDTO = new UpdateUserSettingsDTO(userSettingsId, userId, generalSettings, notificationSettings);
        String userSettingsDTOJson = objectMapper.writeValueAsString(userSettingsDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/user-settings/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSettingsDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<UserSettings> invalidUpdatedUserSettingsOptional = userSettingsRepository.findByUserId(userId);
        if (invalidUpdatedUserSettingsOptional.isPresent() &&
                !invalidUpdatedUserSettingsOptional.get().getNotificationSettings().isSupplierOrdersOn()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/user-settings/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSettingsDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<UserSettings> updatedUserSettingsOptional = userSettingsRepository.findByUserId(userId);
        if (updatedUserSettingsOptional.isEmpty()) {
            fail("Updated userSettings has not been found");
        }
        UserSettings updatedUserSettings = updatedUserSettingsOptional.get();
        assertNotNull(updatedUserSettings);
        assertEquals(userId, updatedUserSettings.getUserId());
        assertEquals(GeneralSettings.InfoLevel.ADVANCED, updatedUserSettings.getGeneralSettings().getInfoLevel());
        assertFalse(updatedUserSettings.getNotificationSettings().isSupplierOrdersOn());
    }

    @Test
    void testDeleteUserSettings() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/user-settings/delete";
        String invalidJWTToken = "Invalid";

        DeleteUserSettingsDTO userSettingsDTO = new DeleteUserSettingsDTO(userSettingsId, userId);
        String userSettingsDTOJson = objectMapper.writeValueAsString(userSettingsDTO);

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSettingsDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<UserSettings> invalidUpdatedUserSettingsOptional = userSettingsRepository.findById(userSettingsId);
        if (invalidUpdatedUserSettingsOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userSettingsDTOJson))
                .andExpect(status().isOk());

        // Assert
        Optional<UserSettings> updatedUserSettingsOptional = userSettingsRepository.findById(userSettingsId);
        if (updatedUserSettingsOptional.isPresent()) {
            fail("UserSettings has not been deleted as expected.");
        }
    }

    private Pair<String, String> createNewUser() {
        // Create user with corresponding organization
        String username = "Test User" + UUID.randomUUID().toString(); // Avoid duplicates
        String passwordHash = passwordEncoder.encode("testPass");
        String uniqueEmail = "testemail" + UUID.randomUUID().toString() + "@gmail.com"; // Avoid duplicates

        User user = User.builder()
                .username(username)
                .passwordHash(passwordHash)
                .email(uniqueEmail)
                .organization(organization)
                .role(User.Role.ADMIN)
                .build();

        user = userRepository.save(user);

        // Get JWT token from user
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(username);
        userDetails.setPassword(passwordHash);
        userDetails.setOrganizationId(organization.getId());
        userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        userDetails.setOrganizationId(organization.getId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        String newJwtToken = tokenProvider.generateToken(authentication);

        return Pair.of(user.getId(), newJwtToken);
    }

}
