package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.dto.Authentication.request.SignupRequest;
import com.darmokhval.Backend_part.dto.Authentication.response.JwtResponse;
import com.darmokhval.Backend_part.dto.Authentication.response.MessageResponse;
import com.darmokhval.Backend_part.entity.ERole;
import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.repository.RoleRepository;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyCustomUserDetails customUserDetails = (MyCustomUserDetails) authentication.getPrincipal();
        List<ERole> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(ERole::valueOf)
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                customUserDetails.getId(),
                customUserDetails.getUsername(),
                customUserDetails.getEmail(),
                roles));

//        return new ResponseEntity<>("User login successfully.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
//        check if username exists in database
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }
//        check if email exists in database
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email is already taken!"));
        }

//        create new user's account
//        not assigning yearOfBirth
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        user.setRole(ERole.ROLE_USER);


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));

    }
}
