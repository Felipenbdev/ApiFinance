package com.finances.finances.controller;


import com.finances.finances.model.LoginRequest;
import com.finances.finances.model.RegisterRequest;
import com.finances.finances.model.User;
import com.finances.finances.model.UserResponse;
import com.finances.finances.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public UserResponse creatUser(@Valid @RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public UserResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        return userService.login(request, httpRequest, httpResponse);
    }
    @GetMapping("/me")
    public UserResponse me() {
        return userService.getCurrentUser();
    }
}
