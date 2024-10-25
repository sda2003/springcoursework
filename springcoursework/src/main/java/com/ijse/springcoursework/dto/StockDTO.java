package com.ijse.springcoursework.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {
    
    @Column(nullable = false)
    private Long itemId;
    private int qty;
    private Double price;
}
