package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "search_history")
public class SearchHistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "entry_date")
    private Date date;

    @Column(name = "entry_text")
    private String entryText;

    public SearchHistoryEntry(){ super(); }

    public SearchHistoryEntry(Date date, String entryText){
        super();
        this.date = date;
        this.entryText = entryText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }
}
