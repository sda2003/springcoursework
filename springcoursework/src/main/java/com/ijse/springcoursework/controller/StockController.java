package com.ijse.springcoursework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.dto.StockDTO;
import com.ijse.springcoursework.entity.Item;
import com.ijse.springcoursework.entity.Stock;
import com.ijse.springcoursework.service.ItemService;
import com.ijse.springcoursework.service.StockService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class StockController {
    
        @Autowired
    private StockService stockService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStockList(HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        

        return ResponseEntity.status(200).body(stockService.getStockList());
    }

    @GetMapping("/stocks/{stockId}")
    public ResponseEntity<Stock> getStockbyId(@PathVariable Long stockId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        return ResponseEntity.status(200).body(stockService.getStockById(stockId));
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> addStock(@RequestBody StockDTO stockDto, HttpServletRequest request) {
        

        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        Stock stock = new Stock();
        
        
        Item item = itemService.getItemById(stockDto.getItemId());
        if (item == null) {
            ResponseEntity.status(404).body(null);
        } 
        
        stock.setItem(item);
        stock.setQty(stockDto.getQty());
        stock.setPrice(stockDto.getPrice());
        
        try {
            stockService.addStock(stock);
        } catch(Exception error) {
            System.out.println(error);
        }

        return ResponseEntity.status(201).body(stock);
    }

    @PutMapping("stocks/{stockId}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long stockId, @RequestBody StockDTO stockDto, HttpServletRequest request) {
        

        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        Stock stock  = new Stock();

        Item item = itemService.getItemById(stockDto.getItemId());
        if (item == null) {
            ResponseEntity.status(404).body(null);
        }
        
        stock.setItem(item);
        stock.setQty(stockDto.getQty());
        stock.setPrice(stockDto.getPrice());
        
        Stock updatedStock = stockService.updateStock(stockId, stock);

        return ResponseEntity.status(200).body(updatedStock);
    }

    @DeleteMapping("/stocks/{stockId}")
    public ResponseEntity<String> deleteStock(@PathVariable Long stockId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        Stock stock = stockService.getStockById(stockId);
        if (stock == null) {
            return ResponseEntity.status(400).body("There is no Stock by id:"+stockId);
        }
        return ResponseEntity.status(200).body("Stock deleted succesfully");
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
