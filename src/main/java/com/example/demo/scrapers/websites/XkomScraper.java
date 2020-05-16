package com.example.demo.scrapers.websites;

import com.example.demo.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class XkomScraper implements Scraper {
    private List<Item> items;
    private final String websiteName;

    public XkomScraper(){
        this.items = new ArrayList<>();
        this.websiteName = "xkom";
    }

    @Override
    public List<Item> searchForItems(String itemName){
        final String url = "https://www.x-kom.pl/szukaj?per_page=90&sort_by=accuracy_desc&q=" + formatItemName(itemName);
        try{
            final Document xkomWebsite = Jsoup.connect(url).get();
            for(Element row : xkomWebsite.select("div.sc-1yu46qn-7.ewNluy.sc-2ride2-0.efeHma")){
                Item item = new Item(websiteName, getItemName(row), getItemPrice(row), getItemRef(row));
                items.add(item);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return this.items;
    }

    @Override
    public String getItemPrice(Element row){
        final String price;

        // check if there is a discount price for the Item
        if(row.select("span.sc-6n68ef-0.sc-6n68ef-3.iertXt").text().equals("")){
            price = row.select("span.sc-6n68ef-0.sc-6n68ef-2.cGJFnx").text();
        }
        else{
            price = row.select("span.sc-6n68ef-0.sc-6n68ef-3.iertXt").text();
        }
        return price;
    }

    @Override
    public String getItemName(Element row){
        return row.select("h3.sc-1yu46qn-12.edAUTq.sc-16zrtke-0.hovdBk").text();
    }

    @Override
    public String getItemRef(Element row){
        return "https://x-kom.pl" + row.select("a.sc-1yu46qn-10.jegHIK.sc-4ttund-0.kTcxmb").attr("href");
    }

    @Override
    public String formatItemName(String ItemName){
        return ItemName.replace(" ", "%20");
    }

    @Override
    public void printItems(){
        for(Item Item : this.items){
            Item.showItem();
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getWebsiteName() {
        return websiteName;
    }
}
