package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.SearchHistoryEntry;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.SearchHistoryRepository;
import com.example.demo.scrapers.WebScraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DatabaseUpdateTask {
    private static final Logger log = LoggerFactory.getLogger(DatabaseUpdateTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private WebScraper scraper;
    private ItemRepository itemRepository;
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public DatabaseUpdateTask(ItemRepository itemRepository, SearchHistoryRepository searchHistoryRepository){
        this.itemRepository = itemRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.scraper = new WebScraper();
    }

    @Scheduled(fixedRate = 7200000L)
    public void updateItemsDatabase(){
        List<SearchHistoryEntry> searchHistory = this.searchHistoryRepository.findAll();
        List<Item> foundItems;
        log.info("database update started on {}", dateFormat.format(new Date()));
        if(searchHistory.size() != 0) {
            for (SearchHistoryEntry searchHistoryEntry : searchHistory) {
                foundItems = scraper.findItems(searchHistoryEntry.getEntryText());
                if(foundItems.size() !=0) {
                    for (Item item: foundItems) {
                        itemRepository.saveUniqueItems(item.getShopName(), item.getName(), item.getPrice(), item.getHref(), item.getImageHref());
                        itemRepository.updatePrices(item.getHref(), item.getPrice());
                    }
                }
            }
            log.info("database update performed on {}", dateFormat.format(new Date()));
        }
        else{
            log.info("database update failed - database is empty : {}", dateFormat.format(new Date()));
        }
    }
}
