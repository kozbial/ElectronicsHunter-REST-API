package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MediaexpertScraper implements Scraper{
    private List<Item> items;
    private final String websiteName;

    public MediaexpertScraper() {
        this.items = new ArrayList<>();
        this.websiteName = "mediaexpert";
    }

    @Override
    public List<Item> searchForItems(String itemName) {
        final String url = "https://www.mediaexpert.pl/search?query[menu_item]=&query[querystring]=" + formatItemName(itemName);
        try{
            final Document mediaexpertWebsite = Jsoup.connect(url).get();
            for(Element row : mediaexpertWebsite.select("div.c-offerBox.is-wide.is-available")){
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
    public double getItemPrice(Element row) {
        String price = row.select("div.c-offerBox_price.is-normalPrice").select("span.a-price_price").text() + row.select("div.c-offerBox_price.is-normalPrice").select("span.a-price_rest").text();
        return formatItemPrice(price);
    }

    @Override
    public double getItemPriceByHref(String href) {
        double itemPrice = 0;
        try{
            final Document mediaexpertWebsite = Jsoup.connect(href).get();
            itemPrice = formatItemPrice(mediaexpertWebsite.select("div.c-offerBox_price.is-normalPrice").select("span.a-price_price").text() + mediaexpertWebsite.select("div.c-offerBox_price.is-normalPrice").select("span.a-price_rest").text());
        }
        catch(Exception ex){
            System.out.println("Failed to get item price " + this.websiteName);
        }
        return itemPrice;
    }

    @Override
    public String getItemName(Element row) {
        return row.select("a.a-typo.is-secondary").text();
    }

    @Override
    public String getItemRef(Element row) {
        return "https://mediaexpert.pl" + row.select("a.a-typo.is-secondary").attr("href");
    }






    @Override
    public String getItemImageHref(Element row){
        Element resultLink = row.select("div.c-offerBox_galleryItem > a > img").first();
        if(resultLink.attr("src").equals("")){
            return "https://mediaexpert.pl" + resultLink.attr("data-src");
        }
        return "https://mediaexpert.pl" + resultLink.attr("src");
    }







    @Override
    public String formatItemName(String itemName) {
        return itemName.replace(" ", "%20");
    }

    @Override
    public double formatItemPrice(String itemPrice) {
        String formattedPrice = itemPrice.replace(",", ".").replace("\\D", "");
        return Double.parseDouble(formattedPrice.replaceAll("[^\\d.]",""));
    }

    @Override
    public void printItems() {
        for(Item Item : this.items){
            Item.showItem();
        }
    }
}
