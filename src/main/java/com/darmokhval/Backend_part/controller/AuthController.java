package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.dto.Authentication.request.SignupRequest;
import com.darmokhval.Backend_part.dto.Authentication.response.JwtResponse;
import com.darmokhval.Backend_part.dto.Authentication.response.MessageResponse;
import com.darmokhval.Backend_part.entity.ERole;
import com.darmokhval.Backend_part.entity.Role;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
//    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
//                          UserService userService,
                          PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
//        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyCustomUserDetails customUserDetails = (MyCustomUserDetails) authentication.getPrincipal();
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
//                .map(item -> item.getAuthority())
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
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
//        too difficult to figure how to add roles, so now just set to all users default role user;
//        Role roleUser = new Role();
//        roleUser.setName(ERole.ROLE_USER);
//        Set<Role> setOfRoles = Collections.singleton(roleUser);

//        BETTER TO USE ENUM!!!!

        user.setRole("ROLE_USER");

//        this is advanced, to check for role in database and if present, attach to user;
//        Set<String> strRoles = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();

//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));

    }
}
