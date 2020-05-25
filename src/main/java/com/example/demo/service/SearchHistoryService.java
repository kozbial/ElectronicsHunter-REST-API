package com.example.demo.service;

import com.example.demo.model.SearchHistoryEntry;

import java.util.List;

public interface SearchHistoryService {
    List<SearchHistoryEntry> getSearchHistory();
    void deleteAllEntries();
}
