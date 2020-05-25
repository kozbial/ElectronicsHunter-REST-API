package com.example.demo.repository;

import com.example.demo.model.SearchHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntry, Long> {

    @Query(value = "select case when (count(*) > 0) then true else false end from search_history where exists(select * from search_history where entry_text = :entryText)", nativeQuery = true)
    boolean findByEntryText(@Param("entryText") String entryText);
}
