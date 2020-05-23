package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.SearchHistoryEntry;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.SearchHistoryRepository;
import com.example.demo.scrapers.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private SearchHistoryRepository searchHistoryRepository;
    private WebScraper scraper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, SearchHistoryRepository searchHistoryRepository){
        this.itemRepository = itemRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.scraper = new WebScraper();
    }

    @Override
    public List<Item> getItemsByName(String itemName) {
        List<Item> itemsFound;
        if(searchHistoryRepository.findByEntryText(itemName)){
            itemsFound = itemRepository.findItemsByName(itemName.toUpperCase());
        }
        else{
            searchHistoryRepository.saveAndFlush(new SearchHistoryEntry(new Date(), itemName));
            itemsFound = this.scraper.findItems(itemName);
            for(Item item: itemsFound){
                itemRepository.saveUniqueItems(item.getShopName(), item.getName(), item.getPrice(), item.getHref());
                itemRepository.updatePrices(item.getHref(), item.getPrice());

                item.setMinPrice(itemRepository.getMinPriceByHref(item.getHref()));
                item.setMaxPrice(itemRepository.getMaxPriceByHref(item.getHref()));
            }
        }
        return itemsFound;
    }

    @Override
    public Item getItemWithUpdatedPrice(String href){
        Item item = itemRepository.getItemByHref(href);
        item.setPrice(this.scraper.getCurrentItemPrice(item));
        itemRepository.updatePrices(item.getHref(), item.getPrice());
        item.setMinPrice(itemRepository.getMinPriceByHref(item.getHref()));
        item.setMaxPrice(itemRepository.getMaxPriceByHref(item.getHref()));
        return item;
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item storeItem(Item item){
        itemRepository.save(item);
        return item;
    }

    @Override
    public void deleteAllData(){
        itemRepository.deleteAll();
        searchHistoryRepository.deleteAll();
    }

    @Override
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }


}
