package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.SaleTransaction;

@Service
public interface SaleTransactionService {
    List<SaleTransaction> getSaleList();
    SaleTransaction getInvoice(Long id);
    SaleTransaction createCart(SaleTransaction sale);
    SaleTransaction updateCart(Long id, SaleTransaction sale);
    void deleteCart(Long id);
}
