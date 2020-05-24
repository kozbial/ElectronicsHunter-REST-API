package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Item;

import com.example.demo.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@Api(value = "ItemsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() throws ResourceNotFoundException{
        List<Item> items = itemService.getAllItems();
        if(items.size() != 0) return new ResponseEntity<>(items, HttpStatus.OK);
        else throw new ResourceNotFoundException("Items database is empty");
    }

    @GetMapping("/id/{id}")
    @ApiOperation("Get the item with specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Item.class)})
    public ResponseEntity<Item> getItemById(@PathVariable(value = "id") Long itemId) throws ResourceNotFoundException {
        Item item = itemService.getItemById(itemId)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found for this id :: "+ itemId));
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable(value = "name") String itemName) throws ResourceNotFoundException {
        List<Item> itemsFound = itemService.getItemsByName(itemName);
        if(itemsFound.size() != 0) return new ResponseEntity<>(itemsFound, HttpStatus.OK);
        else throw new ResourceNotFoundException("No corresponding items found for this name ::" + itemName);
    }

    @GetMapping("/href")
    public ResponseEntity<Item> getCurrentItemPrice(@RequestParam String value){
        return new ResponseEntity<>(this.itemService.getItemWithUpdatedPrice(value), HttpStatus.OK);
    }

    @PostMapping("/store")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        return new ResponseEntity<>(itemService.storeItem(item), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        itemService.deleteAllData();
    }


}
