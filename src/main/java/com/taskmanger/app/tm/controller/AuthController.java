package com.taskmanger.app.tm.controller;

import com.taskmanger.app.tm.entity.User;
import com.taskmanger.app.tm.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/task-manager")
public class AuthController {

    private final UserServices userService;
    private final AuthenticationManager auth;

    @Autowired
    public AuthController(UserServices userService, AuthenticationManager auth) {
        this.userService = userService;
        this.auth = auth;
    }


    //http://localhost:8080/auth/task-manager/signup
    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody User user){

        return ResponseEntity.ok(userService.register(user).toString());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){

        Authentication authenticate = auth.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return ResponseEntity.ok("Sign in Success");
    }

}
