//package com.darmokhval.Backend_part.controller;
//
//import com.darmokhval.Backend_part.entity.User;
//import com.darmokhval.Backend_part.security.MyCustomUserDetails;
//import com.darmokhval.Backend_part.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//public class UserController {
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//    @PostMapping("/users")
//    public User addUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }
////    @PostMapping("/addUsers")
////    public List<User> addUsers(@RequestBody List<User> users) {
////        return userService.saveUsers(users);
////    }
//
//    @GetMapping("/users")
//    @Secured("ADMIN")
//    public List<User> getAllUsers() {
//        return userService.getUsers();
//    }
//    @GetMapping("/users/{id}")
//    public User getUserById(@PathVariable int id) {
//        return userService.getUserById(id);
//    }
//    @GetMapping("/userByName/{name}")
//    public User getUserByName(@PathVariable String name) {
//        return userService.getUserByUsername(name);
//    }
//    @GetMapping("/showUserInfo")
//    public String showUserInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyCustomUserDetails userDetails = (MyCustomUserDetails) authentication.getPrincipal();
////        System.out.println(userDetails.getUser());
//        return "test";
//    }
//
//    @DeleteMapping("/users/{id}")
//    public String deleteUser(@PathVariable int id) {
//        return userService.deleteProduct(id);
//    }
//}
