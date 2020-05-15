package com.example.demo.scrapers;

import com.example.demo.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebScraper {
    private List<Item> itemsFound;
    private XkomScraper xkomScraper;
    private MoreleScraper moreleScraper;
    // todo
    // media expert
    // media markt
    // rtveuroagd

    public WebScraper(){
        this.itemsFound = new ArrayList<>();
        this.xkomScraper = new XkomScraper();
        this.moreleScraper = new MoreleScraper();
    }

    public List<Item> findItems(String itemName){
        this.itemsFound = Stream.of(xkomScraper.searchForItems(itemName),
                moreleScraper.searchForItems(itemName))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        return this.itemsFound;
    }

}
