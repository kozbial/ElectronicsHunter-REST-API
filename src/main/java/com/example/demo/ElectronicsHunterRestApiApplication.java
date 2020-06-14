package com.example.demo;


import com.example.demo.scrapers.websites.MediaexpertScraper;
import com.example.demo.scrapers.websites.MoreleScraper;
import com.example.demo.scrapers.websites.XkomScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElectronicsHunterRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsHunterRestApiApplication.class, args);
    }
}
