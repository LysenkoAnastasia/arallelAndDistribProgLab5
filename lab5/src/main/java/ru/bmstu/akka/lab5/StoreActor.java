package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class TesterActor extends AbstractActor {


    public TesterActor() {
    }


    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMsg.class, this::resultTest)
                .build();
    }
}
