package com.varokas.rx.support;

import java.time.LocalDateTime;

public class JournalEntry {
    private final LocalDateTime logDate;
    private final String message;

    JournalEntry(LocalDateTime logDate, String message) {
        this.logDate = logDate;
        this.message = message;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "logDate=" + logDate +
                ", message='" + message + '\'' +
                '}';
    }
}
