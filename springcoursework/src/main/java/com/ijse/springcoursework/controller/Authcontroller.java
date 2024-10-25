package com.ijse.springcoursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.entity.User;
import com.ijse.springcoursework.repository.UserRepository;
import com.ijse.springcoursework.security.JwtUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class Authcontroller {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/login")
    public String login(@RequestBody User user) {
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtil.generateJwtToken(authentication);

        return jwtToken;
    }
    
}
