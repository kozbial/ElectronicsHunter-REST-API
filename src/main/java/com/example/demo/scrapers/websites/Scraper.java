package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;

public interface Scraper {
    List<Item> searchForItems(String ItemName);
    String getItemPrice(Element row);
    String getItemName(Element row);
    String getItemRef(Element row);
    String formatItemName(String ItemName);
    // debugging method
    void printItems();
}
