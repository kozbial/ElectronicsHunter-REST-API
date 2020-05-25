package com.example.demo.service;

import com.example.demo.model.SearchHistoryEntry;
import com.example.demo.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService{
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public SearchHistoryServiceImpl(SearchHistoryRepository searchHistoryRepository){
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public List<SearchHistoryEntry> getSearchHistory() {
        return this.searchHistoryRepository.findAll();
    }

    @Override
    public void deleteAllEntries(){
        this.searchHistoryRepository.deleteAll();
    }
}
