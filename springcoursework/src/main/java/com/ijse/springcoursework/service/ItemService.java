package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.Item;

@Service
public interface ItemService {
    List<Item> getItemList();
    Item getItemById(Long id);
    Item addItem(Item item);
    Item updateItem(Long id, Item item);
    void deleteItem(Long id);

}
