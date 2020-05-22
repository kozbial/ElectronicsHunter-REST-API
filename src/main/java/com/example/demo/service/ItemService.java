package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> getAllItems();
    List<Item> getItemsByName(String itemName);
    Optional<Item> getItemById(Long id);
    Item storeItem(Item item);
    void deleteAllData();

}
