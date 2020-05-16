package com.example.demo.controller;

import com.example.demo.model.SearchHistoryEntry;
import com.example.demo.repository.SearchHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SearchHistoryController {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    // save history entry
    @PostMapping("history")
    public SearchHistoryEntry addSearchHistoryEntry(@RequestBody SearchHistoryEntry searchEntry){
        return this.searchHistoryRepository.save(searchEntry);
    }

}
