package com.varokas.rx.consensus;

import com.varokas.rx.support.Journal;
import com.varokas.rx.support.JournalEntry;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ConsensusTest {
    private Consensus consensus = new Consensus();
    private Journal journal = new Journal();

    @Test
    public void testPrintConsensusFinishesInLessThan4Seconds() throws Exception {
        consensus.printConsensus(journal);

        List<JournalEntry> entries = journal.getJournalEntries();

        assertTrue(Duration.between(entries.get(3).getLogDate(), entries.get(0).getLogDate()).getSeconds() <= 3);
    }
}
