package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
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
public class ItemsUpdater {
   private static final Logger log = LoggerFactory.getLogger(ItemsUpdater.class);
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

   private WebScraper scraper;
   private ItemRepository itemRepository;

   @Autowired
   public ItemsUpdater(ItemRepository itemRepository){
      this.itemRepository = itemRepository;
      this.scraper = new WebScraper();
   }

   @Scheduled(fixedRate = 7200000L)
   public void updateItemsDatabase(){
      List<Item> allItems = itemRepository.findAll();
      if(allItems.size() != 0) {
         for (Item item : allItems) {
            itemRepository.updatePrices(item.getHref(), scraper.getCurrentItemPrice(item));
         }
         log.info("database update performed on {}", dateFormat.format(new Date()));
      }
      else{
         log.info("database update failed - database is empty : {}", dateFormat.format(new Date()));
      }
   }
}
