package com.varokas.rx.oracle;

import akka.dispatch.ExecutionContexts;
import com.varokas.rx.support.Journal;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.dispatch.Futures.future;

class OracleClient {
    private final Oracle oracle;

    public OracleClient(Oracle oracle) {
        this.oracle = oracle;
    }

    public void getQuoteAndLog(Journal journal) throws Exception {
        ExecutionContext ec = ExecutionContexts.global();


        journal.write("Client: program started");
        journal.write("Client: getting word of the day from Oracle");

        Future<String> f1 = future(() -> {
            //The client is actually have not made request.
            journal.write("Client: Oracle contacted, waiting for response... ");
            return oracle.getQuote();
        }, ec);

        //If we don't wait here, the program exits before the response.
        String result = Await.result(f1, Duration.create(10, TimeUnit.SECONDS));
        journal.write("Quote by Oracle: " + result);
    }
}
