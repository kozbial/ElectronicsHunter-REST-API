package com.example.demo.repository;

import com.example.demo.model.SearchHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntry, Long> {
}
