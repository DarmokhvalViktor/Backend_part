package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.model.dto.Authentication.request.JwtRefreshRequest;
import com.darmokhval.Backend_part.model.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.model.dto.Authentication.request.SignupRequestDTO;
import com.darmokhval.Backend_part.model.dto.Authentication.response.JwtTokenResponse;
import com.darmokhval.Backend_part.model.dto.Authentication.response.UserDetailsResponseDTO;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import com.darmokhval.Backend_part.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.authenticateUser(loginRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    //TODO change signup to "register"
    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.registerUser(signupRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        JwtTokenResponse jwtTokenResponse = authorizationService.refresh(refreshRequest);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @GetMapping("auth/me")
    public ResponseEntity<?> me() {
        SecurityContext contextHolder = SecurityContextHolder.getContext();
        MyCustomUserDetails userDetails = (MyCustomUserDetails) contextHolder.getAuthentication().getPrincipal();
        UserDetailsResponseDTO userDetailsDTO = authorizationService.getUserDetails(userDetails.getUsername());
        return ResponseEntity.ok(userDetailsDTO);
    }
}
