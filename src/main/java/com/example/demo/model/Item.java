package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "items", uniqueConstraints = @UniqueConstraint(columnNames = {"href"}))
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "max_price")
    private double maxPrice;

    @Column(name = "min_price")
    private double minPrice;

    @Column(name = "href")
    private String href;

    @Column(name = "image_href")
    private String imageHref;

    public Item(){
        super();
    }

    public Item(String shopName, String name, double price, String href, String imageHref) {
        super();
        this.shopName = shopName;
        this.name = name;
        this.price = price;
        this.minPrice = price;
        this.maxPrice = price;
        this.href = href;
        this.imageHref = imageHref;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double mixPrice) {
        this.minPrice = mixPrice;
    }

    public void showItem(){
        System.out.println(this.getName() + " " + this.getPrice() + " " + this.getHref());
    }

    public String getImageHref() { return imageHref; }
}
