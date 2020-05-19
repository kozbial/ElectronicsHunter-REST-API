package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class MoreleScraper implements Scraper {
    private List<Item> items;
    private final String websiteName;

    public MoreleScraper(){
        this.items = new ArrayList<>();
        this.websiteName = "morele";
    }

    @Override
    public List<Item> searchForItems(String itemName) {
        final String url = "https://www.morele.net/wyszukiwarka/0/0/,,,,,,,,,,,,/1/?q=" + formatItemName(itemName);
        try{
            final Document moreleWebsite = Jsoup.connect(url).get();
            for(Element row : moreleWebsite.select("div.cat-product.card")){
                Item item = new Item(websiteName, getItemName(row), getItemPrice(row), getItemRef(row));
                this.items.add(item);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return this.items;
    }

    @Override
    public double getItemPrice(Element row) {
        String price = row.attr("data-product-price");
        return formatItemPrice(price);
    }

    @Override
    public String getItemName(Element row) {
        return row.attr("data-product-name");
    }

    @Override
    public String getItemRef(Element row) {
        return "https://morele.net" + row.select("a.cat-product-image.productLink").attr("href");
    }

    @Override
    public String formatItemName(String itemName) {
        return itemName.replace(" ","+");
    }

    @Override
    public double formatItemPrice(String itemPrice) {
        String formattedPrice = itemPrice.replace("\\D", "");
        return Double.parseDouble(formattedPrice.replace(",", "."));
    }

    @Override
    public void printItems() {
        for(Item Item : this.items){
            Item.showItem();
        }
    }
}
