package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.exceptions.*;
import com.darmokhval.Backend_part.models.dto.Authentication.request.JwtRefreshRequest;
import com.darmokhval.Backend_part.models.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.request.SignupRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.response.JwtTokenResponse;
import com.darmokhval.Backend_part.models.entity.ERole;
import com.darmokhval.Backend_part.models.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    public JwtTokenResponse authenticateUser(LoginRequestDTO loginRequestDTO) {
        Optional<User> user = userRepository.findByUsername(loginRequestDTO.getUsername());
        if(user.isEmpty()) {
            throw new UserNotFoundException(loginRequestDTO.getUsername());
        }

//        TODO not working, error with authentication, when trying to login as admin.
//        List<GrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority(user.get().getRole().getRole()));
//        Authentication authenticatedUser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                user.get().getUsername(),
//                user.get().getPassword(),
//                role));
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authenticatedUser);
//        SecurityContextHolder.setContext(context);

        return createJwtTokenResponse(user.get());
    }

    public JwtTokenResponse registerUser(SignupRequestDTO signupRequestDTO) {
//        check if username exists in database
        if(userRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new UsernameAlreadyTaken(signupRequestDTO.getUsername());
        }
//        check if email exists in database
        if(userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new EmailAlreadyTaken(signupRequestDTO.getEmail());
        }
//        create new user's account
        User user = new User(signupRequestDTO.getUsername(),
                signupRequestDTO.getEmail(),
                passwordEncoder.encode(signupRequestDTO.getPassword()));

        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        MyCustomUserDetails userDetails = new MyCustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRole())));
        String accessToken = jwtUtils.generateAccessToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return new JwtTokenResponse(accessToken, refreshToken);
    }

    public JwtTokenResponse refresh(JwtRefreshRequest refreshRequest) {
        String requestRefreshToken = refreshRequest.getRefreshToken();
        Date refreshTokenExpiration = jwtUtils.extractExpiration(requestRefreshToken);
        if(refreshTokenExpiration.before(new Date())) {
            throw new RefreshTokenHasExpired();
        }
        if(!jwtUtils.isRefreshToken(requestRefreshToken)) {
            throw new InvalidRefreshTokenSignature();
        }

        String usernameFromToken = jwtUtils.getUserNameFromJwtToken(refreshRequest.getRefreshToken());
        Optional<User> user = userRepository.findByUsername(usernameFromToken);
        if(user.isEmpty()) {
            throw new UserNotFoundException(usernameFromToken);
        }
        return createJwtTokenResponse(user.get());
    }

    private JwtTokenResponse createJwtTokenResponse(User user) {
        MyCustomUserDetails userDetails = new MyCustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRole())));

        String accessToken = jwtUtils.generateAccessToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        return new JwtTokenResponse(accessToken, refreshToken);
    }
}
