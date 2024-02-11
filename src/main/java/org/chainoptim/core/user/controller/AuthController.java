package org.chainoptim.core.user.controller;

import org.chainoptim.core.user.dto.LoginDTO;
import org.chainoptim.core.user.dto.UserRegistrationDTO;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.service.UserService;
import org.chainoptim.core.user.utils.JwtAuthenticationResponse;
import org.chainoptim.core.user.utils.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDto) {
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
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
        User registeredUser = userService.registerNewUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());

        // Automatically log in user after registration and return JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        registeredUser.getUsername(),
                        registeredUser.getPasswordHash(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                ),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateJWTToken(@RequestBody String jwtToken) {
        logger.info(jwtToken);
        boolean isValid = tokenProvider.validateToken(jwtToken);
        if (isValid) {
            return ResponseEntity.ok().body("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }
}