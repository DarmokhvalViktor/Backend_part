package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.errors.UserNotFoundException;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    In the code below, we get full custom User object using UserRepository,
//    then we build a UserDetails object using static build() method.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return MyCustomUserDetails.build(user);
    }
}
