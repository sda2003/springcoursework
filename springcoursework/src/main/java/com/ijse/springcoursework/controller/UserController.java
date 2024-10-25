package com.ijse.springcoursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.entity.User;
import com.ijse.springcoursework.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public User createUser(@RequestBody User user) {
        
        return userService.createUser(user);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        userService.deleteUserById(userId);
        return ResponseEntity.status(200).body("User account deleted succesfully");
    }




      private String parseJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer") ) {
            return authHeader.substring(7);
        } else {
            System.out.println("Authorization header is missing or does not contain a Bearer token.");
            return null;
        }
    }
    
}
