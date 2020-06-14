package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Query(value = "INSERT INTO items(shop_name, name, price, href, max_price, min_price, image_href) VALUES (:shopName, :name, :productPrice, :productHref, :productPrice, :productPrice, :imageHref) ON CONFLICT (href) DO UPDATE SET price = :productPrice WHERE items.href = :productHref", nativeQuery = true)
    @Transactional
    void saveUniqueItems(@Param("shopName")String shopName, @Param("name") String name, @Param("productPrice") double productPrice, @Param("productHref") String productHref, @Param("imageHref") String imageHref);

    @Modifying
    @Query(value = "UPDATE items SET max_price = CASE WHEN max_price < :productPrice THEN :productPrice ELSE max_price end, min_price = CASE WHEN min_price > :productPrice THEN :productPrice ELSE min_price end, price = CASE WHEN price != :productPrice THEN :productPrice ELSE price end WHERE href = :productHref", nativeQuery = true)
    @Transactional
    void updatePrices(@Param("productHref") String productHref, @Param("productPrice") double productPrice);

    @Query(value = "SELECT max_price FROM items WHERE href = :href", nativeQuery = true)
    double getMaxPriceByHref(@Param("href") String href);

    @Query(value = "SELECT min_price FROM items WHERE href = :href", nativeQuery = true)
    double getMinPriceByHref(@Param("href") String href);

    @Query(value = "SELECT * FROM items WHERE href = :href", nativeQuery = true)
    Item getItemByHref(@Param("href") String href);

    @Query(value = "SELECT * FROM items WHERE to_tsvector(UPPER(items.name)) @@@ plainto_tsquery(:name) ORDER BY items.price", nativeQuery = true)
    List<Item> searchItemsByName(String name);
}
