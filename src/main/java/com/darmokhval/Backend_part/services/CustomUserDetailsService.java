package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.models.entity.User;
import com.darmokhval.Backend_part.exceptions.UserNotFoundException;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
    }

//    In the code below, we get full custom User object using UserRepository,
//    then we build a UserDetails object using static build() method.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
//        Load user and authenticate?
//        List<GrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRole()));
//        Authentication authenticatedUser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                user.getUsername(),
//                user.getPassword(),
//                role));
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authenticatedUser);
//        SecurityContextHolder.setContext(context);
//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        return MyCustomUserDetails.build(user);
    }
}
