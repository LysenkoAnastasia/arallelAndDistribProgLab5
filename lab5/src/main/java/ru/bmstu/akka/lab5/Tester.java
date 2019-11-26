package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.japi.Pair;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class Tester {


    public Tester() {

    }
     public Flow<HttpRequest, HttpResponse, NotUsed> createRoute(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(this::request)
                .map()
    }

    private TestURL request(HttpRequest httpRequest) {
        Query query = httpRequest.getUri().query();
        
        return null;
    }

}
