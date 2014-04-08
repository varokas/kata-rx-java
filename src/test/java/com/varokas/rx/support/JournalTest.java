package com.varokas.rx.support;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JournalTest {
    private final Journal journal = new Journal();

    @Test
    public void testWriteAndRetrieveMessage() {
        journal.write("message1");
        journal.write("message2");

        List<JournalEntry> entries = journal.getJournalEntries();

        assertEquals(2, entries.size());
        assertEquals("message1", entries.get(0).getMessage());
        assertEquals("message2", entries.get(1).getMessage());
        assertTrue(!entries.get(0).getLogDate().isAfter(entries.get(1).getLogDate()));
    }

    @Test
    public void testGetJournalMessagesReturnsString() {
        journal.write("message1");
        journal.write("message2");

        assertEquals(Arrays.asList("message1", "message2"), journal.getJournalMessages());
    }
}
