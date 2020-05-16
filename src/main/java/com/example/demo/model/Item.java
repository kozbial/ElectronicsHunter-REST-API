package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "items", uniqueConstraints = @UniqueConstraint(columnNames = {"href"}))
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "href")
    private String href;

    public Item(){
        super();
    }

    public Item(String shopName, String name, String price, String href) {
        super();
        this.shopName = shopName;
        this.name = name;
        this.price = price;
        this.href = href;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void showItem(){
        System.out.println(this.getName() + " " + this.getPrice() + " " + this.getHref());
    }

}
