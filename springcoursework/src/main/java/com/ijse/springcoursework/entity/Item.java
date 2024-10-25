package com.ijse.springcoursework.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private Double price;

    private String description;
    
    @ManyToOne
    @JoinColumn(name = "item_category_id")
    private ItemCategory category;

    @JsonIgnore
    @ManyToMany(mappedBy = "soldItems")
    private List<SaleTransaction> sale;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Stock> stocks;

    public int getAvailableStock() {
        return stocks.stream()
                     .mapToInt(Stock::getQty)
                     .sum(); 
    }
}
