package com.varokas.rx.consensus;

import akka.dispatch.ExecutionContexts;
import akka.util.BoundedBlockingQueue;
import com.varokas.rx.support.Journal;
import scala.concurrent.ExecutionContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class ConsensusWithBlockingQueue {
    public boolean getConsensus(Journal journal) throws InterruptedException {
        int size = 3;
        BlockingQueue<Boolean> channel = new LinkedBlockingQueue<>();

        ExecutionContext ec = ExecutionContexts.global();

        for (int i=0; i<size; i++) {
            final int index = i;
            ec.execute(() -> {
                try {
                    Thread.sleep(getRandomSleepInMillisec());

                    boolean answer = getRandomAnswer();
                    channel.put(answer);
                    journal.write("Node " + index + " answer: " + answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        int trueCount = 0;
        for (int i=0; i<size; i++) {
            Boolean answer = channel.take();
            trueCount += answer ? 1 : 0;
        }

        boolean result = trueCount > 2;
        journal.write("Consensus: " + result);

        return result;
    }

    private boolean getRandomAnswer() {
        return Math.random() > 0.5;
    }
    private int getRandomSleepInMillisec() {
        return (int)((1 + Math.random() * 2) * 1000);
    }
}
