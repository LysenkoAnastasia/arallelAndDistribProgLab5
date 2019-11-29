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
import akka.stream.javadsl.Source;
import ru.bmstu.akka.lab5.ResultURL;
import ru.bmstu.akka.lab5.TestURL;
import ru.bmstu.akka.lab5.TestUrlMsg;
import scala.compat.java8.FutureConverters;


import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class Tester {
    private ActorRef actorRef;
    final ActorMaterializer materializer;
    public Tester(ActorRef actorRef, ActorMaterializer materializer) {
        this.materializer = materializer;

        this.actorRef = actorRef;
    }
     public Flow<HttpRequest, HttpResponse, NotUsed> createRoute(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(this::request)
                .mapAsync(1, this::processTest)
                .mapAsync()
    }

    private TestURL request(HttpRequest httpRequest) {
        Query query = httpRequest.getUri().query();
        Optional<String> testUrl = query.get("testUrl");
        Optional<String> count = query.get("count");
        return new TestURL(testUrl.get(), Integer.parseInt(count.get()));
    }

    private CompletionStage<ResultURL> processTest(TestURL testURL) {
       return FutureConverters.toJava(Patterns.ask(this.actorRef, testURL, 5000))
               .thenApply(r -> r)
               .thenCompose(res -> {
                   Optional<TestURL>
                   return res.
                       }
                       )



    }

    private CompletionStage<ResultURL> startTest(TestURL testURL) {
        Flow<ResultURL>create
        Source.from(Collections.singletonList(testURL))
                .map()
    }



}
