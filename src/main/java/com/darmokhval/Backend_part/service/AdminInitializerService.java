package com.darmokhval.Backend_part.service;

import com.darmokhval.Backend_part.model.entity.ERole;
import com.darmokhval.Backend_part.model.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminInitializerService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminInitializerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void postInit() {
        Optional<User> user = userRepository.findByUsername("admin");
        if(user.isEmpty()) {
//            insert SQL query add user
            User user2 = new User();
            user2.setUsername("admin");
            user2.setPassword(passwordEncoder.encode("admin123"));
            user2.setEmail("admin@example.com");
            user2.setRole(ERole.ROLE_ADMIN);
            userRepository.save(user2);
        }
    }


}
