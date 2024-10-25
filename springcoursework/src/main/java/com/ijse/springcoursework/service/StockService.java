package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.Stock;

@Service
public interface StockService {
    List<Stock> getStockList();
    Stock getStockById(Long id);
    Stock addStock(Stock stock);
    Stock updateStock(Long id, Stock stock);
    void deleteStock(Long id);

}
