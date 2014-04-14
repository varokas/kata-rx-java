package com.varokas.rx.consensus;

import com.varokas.rx.support.Journal;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consensus {
    public void printConsensus(Journal journal) {
        Observable<Boolean> voter1 = createVoter(1, journal);
        Observable<Boolean> voter2 = createVoter(2, journal);
        Observable<Boolean> voter3 = createVoter(3, journal);

        List<Boolean> seed = new LinkedList<>();
        Observable<List<Boolean>> collectVotedTrue = Observable
                .merge(voter1, voter2, voter3)
                .reduce(seed, (aggregator, element) -> {
                    aggregator.add(element);
                    return aggregator;
                });


        collectVotedTrue.subscribe(result -> {
            System.out.println("Vote Result: " + result);
            System.out.println("Consensus: " + getConsensus(result) );

            journal.write("Consensus: " + getConsensus(result));
        });

        try {
            //Need to block for the thread to finish computation.
            //Is there a way for the Observable to block by itself?
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean getConsensus(List<Boolean> result) {
        return result.stream().filter( e -> e).count() >= 2;
    }

    private Observable<Boolean> createVoter(int number, Journal journal) {
        return Observable.create((Subscriber<? super Boolean> subscriber) -> {
            new Thread(() -> {
                try {
                    Thread.sleep(getRandomSleepInMillisec());
                    boolean randomAnswer = getRandomAnswer();

                    subscriber.onNext(randomAnswer);
                    journal.write("voter " + number + " finished voting with result: " + randomAnswer);
                    subscriber.onCompleted();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private int getRandomSleepInMillisec() {
        return (int)((1 + Math.random() * 2) * 1000);
    }

    private boolean getRandomAnswer() {
        return Math.random() > 0.5;
    }
}
