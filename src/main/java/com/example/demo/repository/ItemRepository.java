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
    List<Item> findByNameContaining(String name);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO items(shop_name,name,price,href) VALUES (?1,?2,?3,?4) ON CONFLICT DO NOTHING")
    @Transactional
    void saveUniqueItems(String shopName, String name, String price, String href);

    @Query(nativeQuery = true, value = "SELECT * FROM items WHERE UPPER(name) LIKE :itemName")
    List<Item> findByName(@Param("itemName") String itemName);
}
