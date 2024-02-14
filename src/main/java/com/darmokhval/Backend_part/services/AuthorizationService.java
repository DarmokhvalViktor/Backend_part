package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.exceptions.RefreshTokenHasExpired;
import com.darmokhval.Backend_part.exceptions.EmailAlreadyTaken;
import com.darmokhval.Backend_part.exceptions.UsernameAlreadyTaken;
import com.darmokhval.Backend_part.models.dto.Authentication.request.BasicRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.request.JwtRefreshRequest;
import com.darmokhval.Backend_part.models.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.request.SignupRequestDTO;
import com.darmokhval.Backend_part.models.dto.Authentication.response.JwtResponse;
import com.darmokhval.Backend_part.models.dto.Authentication.response.JwtTokenResponse;
import com.darmokhval.Backend_part.models.entity.ERole;
import com.darmokhval.Backend_part.models.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }


    public JwtTokenResponse authenticateUser(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
        return new JwtTokenResponse(accessToken, refreshToken);
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signupRequestDTO.getUsername(), signupRequestDTO.getPassword()));

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
//        create new user's account
        User user = new User(signupRequestDTO.getUsername(),
                signupRequestDTO.getEmail(),
                passwordEncoder.encode(signupRequestDTO.getPassword()));

        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);

        return new JwtTokenResponse(accessToken, refreshToken);
    }

    public JwtTokenResponse refresh(JwtRefreshRequest refreshRequest) {
        String requestRefreshToken = refreshRequest.getRefreshToken();
        Date refreshTokenExpiration = jwtUtils.extractExpiration(requestRefreshToken);
        if(refreshTokenExpiration.before(new Date())) {
            throw new RefreshTokenHasExpired();
        }
        String username = jwtUtils.getUserNameFromJwtToken(requestRefreshToken);
//        Why cast to MyCustomUserDetails, if it implements UserDetails interface?
        //Throws error??? Bad credentials, password does not match stored value? TODO not sure about this code
        MyCustomUserDetails customUserDetails = (MyCustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                customUserDetails.getUsername(), customUserDetails.getPassword()));

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
        return new JwtTokenResponse(accessToken, refreshToken);
    }
//
//
//    private Map<String, String> createTokenPairAndRole(LoginRequestDTO loginRequestDTO) {
//        //        What am I comparing? Did I put some user inside context already?
////        Might be that work in filter TODO check
//        //        Do i need to set this into context? TODO check if so
//        //SecurityContextHolder.getContext().setAuthentication(authentication);
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
//        String accessToken = jwtUtils.generateAccessToken(authentication);
//        String refreshToken = jwtUtils.generateRefreshToken(authentication);
//
////        TODO might be bad thing transforming to role
//        MyCustomUserDetails customUserDetails = (MyCustomUserDetails) authentication.getPrincipal();
//        String role = customUserDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .toString();
//
////        Maybe better way to save this?
//        return Map.of("access", accessToken,
//                "refresh",refreshToken,
//                "role", role,
//                "id", customUserDetails.getId().toString(),
//                "username", customUserDetails.getUsername(),
//                "email", customUserDetails.getEmail());
//    }
//    private Map<String, String> createTokenPairAndRole(SignupRequestDTO signupRequestDTO) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                signupRequestDTO.getUsername(), signupRequestDTO.getPassword()));
//        String accessToken = jwtUtils.generateAccessToken(authentication);
//        String refreshToken = jwtUtils.generateRefreshToken(authentication);
//
//        MyCustomUserDetails customUserDetails = (MyCustomUserDetails) authentication.getPrincipal();
//        String role = customUserDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .toString();
//        return Map.of("access", accessToken,
//                "refresh",refreshToken,
//                "role", role,
//                "id", customUserDetails.getId().toString(),
//                "username", customUserDetails.getUsername(),
//                "email", customUserDetails.getEmail());
//    }


}
