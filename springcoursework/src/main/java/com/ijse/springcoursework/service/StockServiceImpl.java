package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.Stock;
import com.ijse.springcoursework.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService{
    
    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> getStockList() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
