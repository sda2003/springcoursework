package com.ijse.springcoursework.service;

import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.User;

@Service
public interface UserService {
    User createUser(User user);  
    void deleteUserById(Long id);

}
