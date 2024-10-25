package com.ijse.springcoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.springcoursework.entity.SaleTransaction;

@Repository
public interface SaleRepository extends JpaRepository<SaleTransaction, Long> {
    
}
