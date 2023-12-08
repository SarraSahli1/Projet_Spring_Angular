package com.example.springbootesprit.service;

import com.example.springbootesprit.config.JwtService;
import com.example.springbootesprit.controller.AuthenticationRequest;
import com.example.springbootesprit.controller.AuthenticationResponse;
import com.example.springbootesprit.entities.EnumRole;
import com.example.springbootesprit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Find the user in the repository
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        // Obtain the user's role from the user entity
        EnumRole userRole = user.getRole();

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        // Create and return the AuthenticationResponse with the user's role
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(userRole)
                .build();
    }



}

