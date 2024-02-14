package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.models.dto.Authentication.request.JwtRefreshRequest;
import com.darmokhval.Backend_part.models.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.request.SignupRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.response.JwtResponse;
import com.darmokhval.Backend_part.models.dto.Authentication.response.JwtTokenResponse;
import com.darmokhval.Backend_part.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    //TODO DO I really need to expose so much information, or stick only with token as a response?

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.authenticateUser(loginRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.registerUser(signupRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        JwtTokenResponse jwtTokenResponse = authorizationService.refresh(refreshRequest);
        return ResponseEntity.ok(jwtTokenResponse);
    }
}
