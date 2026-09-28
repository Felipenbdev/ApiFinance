package com.finances.finances.controller;


import com.finances.finances.model.User;
import com.finances.finances.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(){
        return "Api working";
    }

    @PostMapping
    public User creatUser(@RequestBody User user){

        return  userService.createUser(user);
    }
}
