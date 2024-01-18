//package com.darmokhval.Backend_part.services;
//
//import com.darmokhval.Backend_part.entity.User;
//import com.darmokhval.Backend_part.errors.UserNotFoundException;
//import com.darmokhval.Backend_part.repository.UserRepository;
//import com.darmokhval.Backend_part.security.MyCustomUserDetails;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class UserService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public User saveUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole("ROLE_USER");
//        return userRepository.save(user);
//    }
////    public List<User> saveUsers(List<User> users) {
////        return userRepository.saveAll(users);
////    }
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }
//    public User getUserById(int id) {
//        return userRepository.findById(id).orElse(null);
//    }
//    public User getUserByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user.isEmpty()) {
//            throw new UserNotFoundException(username);
//        }
//        return user.get();
//    }
//    public String deleteProduct(int id) {
//        userRepository.deleteById(id);
//        return "user removed " + id;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new MyCustomUserDetails(user.get());
//    }
//}
