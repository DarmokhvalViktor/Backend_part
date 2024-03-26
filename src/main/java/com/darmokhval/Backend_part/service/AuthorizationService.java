package com.darmokhval.Backend_part.service;

import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.exception.*;
import com.darmokhval.Backend_part.mapper.MainMapper;
import com.darmokhval.Backend_part.model.dto.Authentication.request.JwtRefreshRequest;
import com.darmokhval.Backend_part.model.dto.Authentication.request.LoginRequestDTO;
import com.darmokhval.Backend_part.model.dto.Authentication.request.SignupRequestDTO;
import com.darmokhval.Backend_part.model.dto.Authentication.response.JwtTokenResponse;
import com.darmokhval.Backend_part.model.dto.Authentication.response.UserDetailsResponseDTO;
import com.darmokhval.Backend_part.model.entity.ERole;
import com.darmokhval.Backend_part.model.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MainMapper mainMapper;

    public JwtTokenResponse loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException(loginRequestDTO.getUsername()));
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return createJwtTokenResponse(user);
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
        user = userRepository.save(user);
        return createJwtTokenResponse(user);
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

    public UserDetailsResponseDTO getUserDetails(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return mainMapper.convertUserToDTO(user.get());
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
