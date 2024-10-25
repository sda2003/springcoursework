package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.SaleTransaction;
import com.ijse.springcoursework.repository.SaleRepository;

@Service
public class SaleTransactionServiceImpl implements SaleTransactionService{
    
    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<SaleTransaction> getSaleList() {
        return saleRepository.findAll();
    }

    @Override
    public SaleTransaction getInvoice(Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public SaleTransaction createCart(SaleTransaction sale) {
        return saleRepository.save(sale);
    }

    @Override
    public SaleTransaction updateCart(Long id, SaleTransaction sale) {
        SaleTransaction existingSale = saleRepository.findById(id).orElse(null);

        if (existingSale == null) {
            return null;
        } else {
            
            existingSale.setTotalPrice(sale.getTotalPrice());
            existingSale.setSoldItems(sale.getSoldItems());    
            
            return saleRepository.save(existingSale);
        }
    }

    @Override
    public void deleteCart(Long id) {
        saleRepository.deleteById(id);
    }
}
