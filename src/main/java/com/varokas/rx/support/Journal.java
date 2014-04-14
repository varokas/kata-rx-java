package com.varokas.rx.support;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Journal {
    private final List<JournalEntry> journalEntries = new LinkedList<>();

    public void write(String message) {
        journalEntries.add(new JournalEntry(LocalDateTime.now(), message));
    }

    public List<JournalEntry> getJournalEntries() {
        return Collections.unmodifiableList(journalEntries);
    }

    public List<String> getJournalMessages() {
        return journalEntries.stream().map(JournalEntry::getMessage).collect(Collectors.toList());
    }
}
