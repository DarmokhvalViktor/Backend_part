package com.darmokhval.Backend_part.config;

import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomAuthenticationManager implements AuthenticationManager {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> user = userRepo.findByUsername(authentication.getName());
        if (user.isPresent()) {
            if (passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword())) {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//                for (Role role : user.get().getRoleSet()) {
//                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
//                }
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                        authentication.getCredentials(), grantedAuthorityList);
            } else {
                throw new BadCredentialsException("Wrong Password");
            }
        } else {
            throw new BadCredentialsException("Wrong UserName");
        }
    }
}