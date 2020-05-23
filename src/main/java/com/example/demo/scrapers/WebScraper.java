package com.example.demo.scrapers;

import com.example.demo.model.Item;
import com.example.demo.scrapers.websites.MoreleScraper;
import com.example.demo.scrapers.websites.XkomScraper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebScraper {

    private XkomScraper xkomScraper;
    private MoreleScraper moreleScraper;
    // media expert
    // media markt
    // rtveuroagd

    public WebScraper(){
        this.xkomScraper = new XkomScraper();
        this.moreleScraper = new MoreleScraper();
    }

    // merge lists of found items from different scrapers
    public List<Item> findItems(String itemName){
        return Stream.of(xkomScraper.searchForItems(itemName),
                moreleScraper.searchForItems(itemName))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

    public double getCurrentItemPrice(Item item){
        double itemPrice;
        switch(item.getShopName()){
            case "xkom":
                itemPrice = xkomScraper.getItemPriceByHref(item.getHref());
                break;
            case "morele":
                itemPrice = moreleScraper.getItemPriceByHref(item.getHref());
                break;
            default:
                System.out.println("Scraper for " + item.getShopName() + " not found.");
                return item.getPrice();
        }
        return itemPrice;
    }

}
