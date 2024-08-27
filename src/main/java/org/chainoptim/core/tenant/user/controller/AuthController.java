package org.chainoptim.core.tenant.user.controller;

import org.chainoptim.core.general.email.service.EmailVerificationService;
import org.chainoptim.core.tenant.user.dto.LoginDTO;
import org.chainoptim.core.tenant.user.dto.UserRegistrationDTO;
import org.chainoptim.core.tenant.user.jwt.JwtAuthenticationResponse;
import org.chainoptim.core.tenant.user.jwt.JwtTokenProvider;
import org.chainoptim.core.tenant.user.service.UserWriteService;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.model.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserWriteService userWriteService;
    private final EmailVerificationService emailVerificationService;
    private final JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserWriteService userWriteService,
                            EmailVerificationService emailVerificationService,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userWriteService = userWriteService;
        this.emailVerificationService = emailVerificationService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
        User registeredUser = userWriteService.registerNewUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());

        // Automatically log in user after registration and return JWT
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(registeredUser.getUsername());
        userDetails.setPassword(registeredUser.getPasswordHash());
        userDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token,
                                              @RequestParam(required = false) boolean isInOrganization,
                                              @RequestParam(required = false) String newPassword) {
        String response = emailVerificationService.verifyAccountEmail(token, isInOrganization, newPassword);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateJWTToken(@RequestBody String jwtToken) {
        logger.info(jwtToken);
        boolean isValid = tokenProvider.validateToken(jwtToken);
        if (isValid) {
            return ResponseEntity.ok().body("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }

    @PostMapping("/get-username-from-token")
    public ResponseEntity<String> getUsernameFromToken(@RequestBody String jwtToken) {
        logger.info(jwtToken);
        String username = tokenProvider.getUsernameFromJWT(jwtToken);
        if (username != null) {
            return ResponseEntity.ok().body(username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }
}