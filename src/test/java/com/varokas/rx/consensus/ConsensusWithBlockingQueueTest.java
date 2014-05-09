package com.varokas.rx.consensus;

import com.varokas.rx.support.Journal;
import com.varokas.rx.support.JournalEntry;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ConsensusWithBlockingQueueTest {
    @Test
    public void testConcensus() throws Exception {
        Journal journal = new Journal();

        new ConsensusWithBlockingQueue().getConsensus(journal);

        List<JournalEntry> entries = journal.getJournalEntries();
        assertTrue(Duration.between(entries.get(3).getLogDate(), entries.get(0).getLogDate()).getSeconds() <= 3);
    }
}
