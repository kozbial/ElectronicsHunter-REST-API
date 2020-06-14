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
                Item item = new Item(websiteName, getItemName(row), getItemPrice(row), getItemRef(row), getItemImageHref(row));
                this.items.add(item);
            }
        }
        catch(Exception ex){
            System.out.println("Could not search for items at " + this.websiteName + " page");
        }
        return this.items;
    }

    @Override
    public double getItemPriceByHref(String href){
        double itemPrice = 0;
        try{
            final Document moreleWebsite = Jsoup.connect(href).get();
            itemPrice = formatItemPrice(moreleWebsite.select("div.price-new").attr("content"));
        }
        catch(Exception ex){
            System.out.println("Failed to get price of item " + this.websiteName);
        }
        return itemPrice;
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
    public String getItemImageHref(Element row) {
        return row.select("img.product-image").attr("src");
    }

    @Override
    public void printItems() {
        for(Item Item : this.items){
            Item.showItem();
        }
    }

}
