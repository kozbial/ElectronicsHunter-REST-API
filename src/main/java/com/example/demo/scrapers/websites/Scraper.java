package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;

public interface Scraper {
    List<Item> searchForItems(String itemName);
    double getItemPrice(Element row);
    String getItemName(Element row);
    String getItemRef(Element row);
    String formatItemName(String itemName);
    double formatItemPrice(String itemPrice);
    // debugging method
    void printItems();
}
