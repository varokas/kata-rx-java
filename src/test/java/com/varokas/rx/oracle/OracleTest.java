package com.varokas.rx.oracle;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OracleTest {
    private final Oracle oracle = new Oracle();

    /**
     * This should be two test cases, but made it one to save time
     */
    @Test
    public void testGetQuoteTakesLongTimeAndReturnsTheQuote() {
        long start = System.currentTimeMillis();

        String returnedQuote = oracle.getQuote();

        long end = System.currentTimeMillis();

        assertTrue( end - start > 1500 );
        assertTrue( Oracle.quotes.contains(returnedQuote) );

    }
}
