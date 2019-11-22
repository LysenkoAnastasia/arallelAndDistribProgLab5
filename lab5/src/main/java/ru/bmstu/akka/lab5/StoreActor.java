package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {

    private Map<TestURL, Integer> storage;


    public StoreActor() {
        this.storage = new HashMap<>();
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ResultURL.class,this::result)
                .match(TestURL.class,this::getTest)
                .build();
    }

    private void result(ResultURL resultURL) {

    }

    private void getTest(TestURL testURL) {

    }

}
