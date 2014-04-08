package com.varokas.rx.oracle;

import com.varokas.rx.support.Journal;
import com.varokas.rx.support.JournalEntry;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OracleClientTest {
    private final Journal journal = new Journal();
    private final Oracle oracle = new Oracle();

    private final OracleClient oracleClient = new OracleClient(oracle);

    @Test
    public void testGetAndPrintInOrder() throws Exception {
        oracleClient.getQuoteAndLog(journal);

        assertMessageOrder(journal.getJournalMessages());
        assertDuration(journal.getJournalEntries());
    }

    private void assertMessageOrder(List<String> journalTexts) {
        assertEquals(4, journalTexts.size());

        assertEquals("Client: program started", journalTexts.get(0));
        assertEquals("Client: getting word of the day from Oracle", journalTexts.get(1));
        assertEquals("Client: Oracle contacted, waiting for response... ", journalTexts.get(2));
        assertTrue(journalTexts.get(3).startsWith("Quote by Oracle: "));
    }

    private void assertDuration(List<JournalEntry> journalEntries) {
        List<LocalDateTime> timeStamps = journalEntries.stream().map(JournalEntry::getLogDate).collect(Collectors.toList());

        assertTrue(Duration.between(timeStamps.get(1), timeStamps.get(2)).getSeconds() < 1);
        assertTrue(Duration.between(timeStamps.get(2), timeStamps.get(3)).getSeconds() > 1);
    }
}
