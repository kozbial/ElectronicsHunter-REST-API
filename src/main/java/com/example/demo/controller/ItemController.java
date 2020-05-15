package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    //get all items
    @GetMapping("items")
    public List<Item> getAllItems(){
        return this.itemRepository.findAll();
    }

    // get item by id
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemByName(@PathVariable(value = "id") Long itemId) throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found for this id :: "+ itemId));
        return ResponseEntity.ok().body(item);
    }

    // save item
    @PostMapping("items")
    public Item addItem(@RequestBody Item item){
        return this.itemRepository.save(item);
    }


}
