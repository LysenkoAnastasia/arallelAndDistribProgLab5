package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class Tester {
    private ActorRef actorRef;
    public Tester() {

    }
     public Flow<HttpRequest, HttpResponse, NotUsed> createRoute(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(this::request)
                .mapAsync()
    }

    private TestURL request(HttpRequest httpRequest) {
        Query query = httpRequest.getUri().query();
        Optional<String> testUrl = query.get("testUrl");
        Optional<String> count = query.get("count");
        return new TestURL(testUrl.get(), Integer.parseInt(count.get()));
    }

    private CompletionStage<Integer> processTest(TestURL testURL) {
        Patterns.ask(actorRef, )

    }



}
