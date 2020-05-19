package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

import com.example.demo.scrapers.WebScraper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(value = "ItemsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    //get all items
    @GetMapping("/items")
    public List<Item> getAllItems(){
        return this.itemRepository.findAll();
    }

    // get item by id
    @GetMapping("/item/{id}")
    @ApiOperation("Get the item with specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Item.class)})
    public ResponseEntity<Item> getItemById(@PathVariable(value = "id") Long itemId) throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found for this id :: "+ itemId));
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/items/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable(value = "name") String itemName) throws ResourceNotFoundException {
        // if searched phrase is in history search call findByName, cause all of items are updated every 2 hours
        // else call webScraper to search and save unique found items

        List<Item> itemsFound = itemRepository.findByName(itemName.toUpperCase());
        if(itemsFound.size() == 0){
            WebScraper scraper = new WebScraper();
            itemsFound = scraper.findItems(itemName);
            for(Item item: itemsFound){
                try {
                    itemRepository.saveUniqueItems(item.getShopName(),item.getName(),item.getPrice(),item.getHref());
                    itemRepository.updateMinAndMaxPrice(item.getHref(), item.getPrice());
                }
                catch(DataIntegrityViolationException ignored){
                }
            }
        }
        return ResponseEntity.ok().body(itemsFound);
    }

    // save item
    @PostMapping("/items")
    public Item addItem(@RequestBody Item item){
        return this.itemRepository.save(item);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        this.itemRepository.deleteAll();
    }


}
