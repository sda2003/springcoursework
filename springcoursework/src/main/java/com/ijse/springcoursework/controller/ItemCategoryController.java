package com.ijse.springcoursework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.entity.ItemCategory;
import com.ijse.springcoursework.service.ItemCategoryService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ItemCategoryController {
    
    @Autowired
    private ItemCategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<ItemCategory>> getCategoryList(HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return ResponseEntity.status(200).body(categoryService.getCategoryList());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ItemCategory> getCategoryById(@PathVariable Long categoryId, HttpServletRequest request) {
       
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        return ResponseEntity.status(200).body(categoryService.getCategoryById(categoryId));
    }
    

    @PostMapping("/categories")
    public ResponseEntity<ItemCategory> addCategory(@RequestBody ItemCategory category, HttpServletRequest request) {

        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        ItemCategory newCategory = categoryService.addCategory(category);

        return ResponseEntity.status(201).body(newCategory);
    }

    @DeleteMapping("/categories/{categoryId}") 
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        
        ItemCategory category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            return ResponseEntity.status(400).body("There is no category by id:"+categoryId);
        }
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(200).body("Category deleted succesfully");
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
