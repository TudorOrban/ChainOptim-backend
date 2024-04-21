package org.chainoptim.testutil;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.jwt.JwtTokenProvider;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestDataSeederService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public TestDataSeederService(
        UserRepository userRepository,
        OrganizationRepository organizationRepository,
        JwtTokenProvider tokenProvider,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Pair<Integer, String> seedDatabaseWithTenant() {
        // Create organization
        Organization organization = Organization.builder()
                .name("Test Org")
                .subscriptionPlanTier(Organization.SubscriptionPlanTier.PRO)
                .build();

        organization = organizationRepository.save(organization);
        Integer organizationId = organization.getId();

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

        userRepository.save(user);

        // Get JWT token from user
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(username);
        userDetails.setPassword(passwordHash);
        userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        userDetails.setOrganizationId(organizationId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        String token = tokenProvider.generateToken(authentication);

        return Pair.of(organizationId, token);
    }
}
