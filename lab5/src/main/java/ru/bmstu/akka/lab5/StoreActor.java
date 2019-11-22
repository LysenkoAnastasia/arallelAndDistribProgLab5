package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.Map;

public class StoreActor extends AbstractActor {

    private Map<String, Integer> storage;



    public StoreActor() {

    }


    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMsg.class, this::resultTest)
                .build();
    }
}
