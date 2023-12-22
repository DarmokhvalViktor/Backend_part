package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public List<User> saveUsers(List<User> users) {
        return userRepository.saveAll(users);
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }
    public String deleteProduct(int id) {
        userRepository.deleteById(id);
        return "user removed " + id;
    }
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }
}
