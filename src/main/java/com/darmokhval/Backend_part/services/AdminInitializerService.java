package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.entity.ERole;
import com.darmokhval.Backend_part.entity.Role;
import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminInitializerService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminInitializerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    TODO this is the way to assure that admin is inserted at the start of the application. Or maybe I should use Flyway to
//    TODO assure that DB is filled & created exactly how I want.
    @PostConstruct
    public void postInit() {
        Optional<User> user = userRepository.findByUsername("admin");
        if(user.isEmpty()) {
//            insert SQL query add user
            User user2 = new User();
            user2.setUsername("admin");
            user2.setPassword(passwordEncoder.encode("admin123"));
            user2.setEmail("admin@example.com");
//            Role roleUser = new Role();
//            roleUser.setName(ERole.ROLE_ADMIN);
//            Set<Role> setOfRoles = Collections.singleton(roleUser);
            user2.setRole("ROLE_ADMIN");
//            .....
            userRepository.save(user2);
        }
    }


}
