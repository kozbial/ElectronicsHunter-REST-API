package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.nodes.Element;
import java.util.List;

public interface Scraper {
    List<Item> searchForItems(String itemName);
    double getItemPrice(Element row);
    double getItemPriceByHref(String href);
    String getItemName(Element row);
    String getItemRef(Element row);

    String formatItemName(String itemName);
    double formatItemPrice(String itemPrice);
    String getItemImageHref(Element row);
    void printItems(); //debugging method
}
