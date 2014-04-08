package com.varokas.rx.oracle;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Oracle {
    private static final int PROCESSING_TIME = 2000;
    public static final List<String> quotes = Arrays.asList(
        "Insanity: doing the same thing over and over again and expecting different results.",
        "A person who never made a mistake never tried anything new.",
        "Gravitation is not responsible for people falling in love.",
        "Look deep into nature, and then you will understand everything better."
    );

    private final Random random = new Random();

    public String getQuote() {
        int idx = random.nextInt(quotes.size());

        sleep();

        return quotes.get(idx);
    }

    private void sleep() {
        try {
            Thread.sleep(PROCESSING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
