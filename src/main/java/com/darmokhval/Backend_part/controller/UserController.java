package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.entity.User;
import com.darmokhval.Backend_part.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @PostMapping("/addUsers")
    public List<User> addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
    }

    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }
    @GetMapping("/UserById/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }
    @GetMapping("/UserByName/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteProduct(id);
    }
}
