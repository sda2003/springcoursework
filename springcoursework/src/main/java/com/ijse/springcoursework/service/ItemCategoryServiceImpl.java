package com.ijse.springcoursework.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.ItemCategory;
import com.ijse.springcoursework.error.CustomException;
import com.ijse.springcoursework.repository.ItemCategoryRepository;


@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {
    
    @Autowired
    private ItemCategoryRepository categoryRepository;

    @Override
    public List<ItemCategory> getCategoryList() {
        return categoryRepository.findAll();
    }


    @Override
    public ItemCategory getCategoryById(Long id) {
        Optional<ItemCategory> category = categoryRepository.findById(id);
        return category
            .orElseThrow(() -> new CustomException("Category Not Found with id: " + id));
    }

    

    @Override
    public ItemCategory addCategory(ItemCategory category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
