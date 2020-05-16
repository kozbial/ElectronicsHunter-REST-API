package com.example.demo;

import com.example.demo.scrapers.WebScraper;
import com.example.demo.scrapers.websites.MoreleScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectronicsHunterRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsHunterRestApiApplication.class, args);
    }

}
