package com.ijse.springcoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.springcoursework.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
}
