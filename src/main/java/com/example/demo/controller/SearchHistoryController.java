package com.example.demo.controller;

import com.example.demo.model.SearchHistoryEntry;
import com.example.demo.repository.SearchHistoryRepository;

import com.example.demo.service.SearchHistoryServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class SearchHistoryController {
    private final SearchHistoryServiceImpl searchHistoryService;

    @Autowired
    public SearchHistoryController(SearchHistoryServiceImpl searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SearchHistoryEntry>> getSearchHistory(){
        return new ResponseEntity<>(this.searchHistoryService.getSearchHistory(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        this.searchHistoryService.deleteAllEntries();
    }

}
