package org.chainoptim.core.user.controller;

import org.chainoptim.core.user.utils.JwtAuthenticationResponse;
import org.chainoptim.core.user.utils.JwtTokenProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @Data
    static class LoginDto {
        private String username;
        private String password;
    }
}



//    @PostMapping("/api/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
//        try {
//            UsernamePasswordAuthenticationToken authReq =
//                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
//            authenticationManager.authenticate(authReq);
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "User authenticated successfully");
//            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            Map<String, String> response = new HashMap<>();
//            response.put("error", "Authentication failed");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }