package com.ijse.springcoursework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.dto.ItemReqDTO;
import com.ijse.springcoursework.entity.Item;
import com.ijse.springcoursework.entity.ItemCategory;
import com.ijse.springcoursework.service.ItemCategoryService;
import com.ijse.springcoursework.service.ItemService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCategoryService categoryService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItemList(HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        
        return ResponseEntity.status(200).body(itemService.getItemList());
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Long itemId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }

        
        return ResponseEntity.status(200).body(itemService.getItemById(itemId));
    }
    


    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody ItemReqDTO itemRequestDTO, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        
        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        item.setPrice(itemRequestDTO.getPrice());
        item.setDescription(itemRequestDTO.getDescription());

        ItemCategory category = categoryService.getCategoryById(itemRequestDTO.getItemCategoryId());

        item.setCategory(category);

        Item createdItem = itemService.addItem(item);

        return ResponseEntity.status(201).body(createdItem);
        
    }

    @PutMapping("items/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @RequestBody ItemReqDTO itemReqDTO, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }

        
        Item item = new Item();
        item.setName(itemReqDTO.getName());
        item.setPrice(itemReqDTO.getPrice());
        item.setDescription(itemReqDTO.getDescription());

        ItemCategory category = categoryService.getCategoryById(itemReqDTO.getItemCategoryId());

        item.setCategory(category);

        Item updatedItem = itemService.updateItem(itemId, item);

        return ResponseEntity.status(200).body(updatedItem);
        
    }

    @DeleteMapping("items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return ResponseEntity.status(400).body("There is no item by id:"+itemId);
        }
        itemService.deleteItem(itemId);
        return ResponseEntity.status(200).body("Item deleted succesfully");
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
