package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.ItemCategory;

@Service
public interface ItemCategoryService {
    List<ItemCategory> getCategoryList();
    ItemCategory addCategory(ItemCategory category);
    ItemCategory getCategoryById(Long id);
    void deleteCategory(Long id);
}
