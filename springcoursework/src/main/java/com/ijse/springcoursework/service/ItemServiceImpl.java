package com.ijse.springcoursework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springcoursework.entity.Item;
import com.ijse.springcoursework.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItemList() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
            .orElse(null);
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item item) {
         Item existingItem = itemRepository.findById(id).orElse(null);

         if (existingItem == null) {
            return null;
         } else {
            existingItem.setName(item.getName());
            existingItem.setPrice(item.getPrice());
            existingItem.setDescription(item.getDescription());
            existingItem.setCategory(item.getCategory());

            return itemRepository.save(existingItem);
         }
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
