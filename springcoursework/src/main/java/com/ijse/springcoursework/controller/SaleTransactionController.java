package com.ijse.springcoursework.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springcoursework.dto.SaleDTO;
import com.ijse.springcoursework.entity.Item;
import com.ijse.springcoursework.entity.SaleTransaction;
import com.ijse.springcoursework.entity.Stock;
import com.ijse.springcoursework.entity.User;
import com.ijse.springcoursework.repository.UserRepository;
import com.ijse.springcoursework.security.JwtUtil;
import com.ijse.springcoursework.service.ItemService;
import com.ijse.springcoursework.service.SaleTransactionService;
import com.ijse.springcoursework.service.StockService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
public class SaleTransactionController {
    
    @Autowired
    private SaleTransactionService saleService;

    @Autowired
    private ItemService itemService;

    @Autowired StockService stockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;



    @GetMapping("/sales")
    public ResponseEntity<List<SaleTransaction>> getSaleList() {
        return ResponseEntity.status(200).body(saleService.getSaleList());
    }


    @GetMapping("/sales/{saleId}")
    public ResponseEntity<SaleTransaction> getInvoice(@PathVariable Long saleId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        SaleTransaction invoice = saleService.getInvoice(saleId);
        if (invoice == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.status(200).body(invoice);
    }
    

    @PostMapping("/sales")
    public ResponseEntity<SaleTransaction> createCart(@RequestBody SaleDTO saleDto, HttpServletRequest request) {

        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        String username = jwtUtil.extractUsernameFromJwt(jwtToken);
        User user = userRepository.findByUsername(username).orElse(null);
        
        SaleTransaction cart = new SaleTransaction();
        cart.setTotalPrice(0.0);

        List<Long> itemIds = saleDto.getItemIds();
        List<Item> soldItems = new ArrayList<>();

        if (itemIds == null || itemIds.isEmpty()) {
            return ResponseEntity.status(400).body(null); // System.out.println(haven't given Ids);
        }

        

        for(Long itemId : itemIds) {
            Item item = itemService.getItemById(itemId);

            if(item != null && item.getAvailableStock() >0) {
                soldItems.add(item);
                cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());


                boolean stockUpdated = false;
                for (Stock stock : item.getStocks()) {
                    if (stock.getQty() > 0) {
                        stock.setQty(stock.getQty() - 1);
                        stockService.updateStock(itemId, stock);
                        stockUpdated = true;
                        break;
                    }
                }

                if (!stockUpdated) {
                    return ResponseEntity.status(400).body(null);  //System.out.println(stock not uppdated);
                }

            } else {
                return ResponseEntity.status(400).body(null);  //System.out.println(no item by the id or item not available); 
            }
        }

        cart.setSoldItems(soldItems);

        cart.setUser(user);

        saleService.createCart(cart);

        return ResponseEntity.status(201).body(cart);

    }

    @PutMapping("sales/{saleId}")
    public ResponseEntity<SaleTransaction> updateCart(@PathVariable Long saleId, @RequestBody SaleDTO saleDTO, HttpServletRequest request ) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }

        String username = jwtUtil.extractUsernameFromJwt(jwtToken);
        User user = userRepository.findByUsername(username).orElse(null);


        SaleTransaction existingCart = saleService.getInvoice(saleId);

        if(user == existingCart.getUser()){

            existingCart.setTotalPrice(0.0);

            List<Long> itemIds = saleDTO.getItemIds();
            List<Item> soldItems = new ArrayList<>();


            itemIds.forEach(itemId -> {
                Item item = itemService.getItemById(itemId);

                if(item != null) {
                    soldItems.add(item);
                    existingCart.setTotalPrice(existingCart.getTotalPrice() + item.getPrice());
                }
            });

            existingCart.setSoldItems(soldItems);

            saleService.updateCart(saleId, existingCart);

            return ResponseEntity.status(200).body(existingCart);
        
        } else {
            return ResponseEntity.status(403).body(null);
        }


    }

    @DeleteMapping("/sales/{saleId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long saleId, HttpServletRequest request) {
        
        String jwtToken = parseJwtFromHeader(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }


        SaleTransaction cart = saleService.getInvoice(saleId);
        if (cart == null) {
            ResponseEntity.status(400).body("There is nor cart by id:"+saleId );
        }
        saleService.deleteCart(saleId);
        return ResponseEntity.status(200).body("Cart deleted succesfully");
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
