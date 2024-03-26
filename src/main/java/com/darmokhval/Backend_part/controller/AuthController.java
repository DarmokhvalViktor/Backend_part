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
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/auth/login")
    public ResponseEntity<JwtTokenResponse> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }
    @PostMapping("/auth/register")
    public ResponseEntity<JwtTokenResponse> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        JwtTokenResponse jwtTokenResponse = authorizationService.registerUser(signupRequestDTO);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtTokenResponse> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        JwtTokenResponse jwtTokenResponse = authorizationService.refresh(refreshRequest);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @GetMapping("auth/me")
    public ResponseEntity<UserDetailsResponseDTO> me() {
        SecurityContext contextHolder = SecurityContextHolder.getContext();
        MyCustomUserDetails userDetails = (MyCustomUserDetails) contextHolder.getAuthentication().getPrincipal();
        UserDetailsResponseDTO userDetailsDTO = authorizationService.getUserDetails(userDetails.getUsername());
        return ResponseEntity.ok(userDetailsDTO);
    }
}
//TODO create controller for users. To delete, update users, only admin.
// Plus endpoint for manager or smth like that, only admin can create them.

//TODO create new entity (type of question) for user to decide what should do with that question:
// choose one right answer, choose several right answers, type missing word, etc.

//TODO add Oauth2.0 to frontend and backend, need to receive(in backend) request, find if it from Oauth2.0, and login
// with different methods in service layer. Extract info from google token, and create my own, than return back to front.

//TODO change and create new service/controller layer for question_type.