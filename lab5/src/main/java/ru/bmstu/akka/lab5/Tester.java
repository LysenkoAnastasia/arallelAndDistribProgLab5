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
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.asynchttpclient.AsyncHttpClient;
import ru.bmstu.akka.lab5.ResultURL;
import ru.bmstu.akka.lab5.TestURL;
import ru.bmstu.akka.lab5.TestUrlMsg;
import scala.compat.java8.FutureConverters;


import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Tester {
    private ActorRef actorRef;
    final ActorMaterializer materializer;
    final AsyncHttpClient asyncHttpClient;

    public Tester(AsyncHttpClient asyncHttpClient, ActorSystem system, ActorMaterializer materializer) {
        this.materializer = materializer;
        this.asyncHttpClient = asyncHttpClient;
        this.actorRef = system.;
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
               .thenCompose(res ->
                       }
                       )



    }

    private CompletionStage<ResultURL> startTest(TestURL testURL) {
        final AsyncHttpClient asyncHttpClient;
        final Sink<TestURL, CompletionStage<Integer>> sink = createSink();
        asyncHttpClient.close();

        return Source.from(Collections.singletonList(testURL))
                //.mapConcat(test -> Collections.nCopies(test.getCount(), test.getUrl()))
                .toMat(sink, Keep.right())
                .run(materializer)
                .thenApply(sum -> {
                    ResultURL resultURL = new ResultURL(testURL, sum/testURL.getCount());
                    actorRef.tell(resultURL, ActorRef.noSender());
                    return resultURL;
                });
    }

    private  Sink<TestURL, CompletionStage<Integer>> createSink() {
        Flow.<TestURL>create()
                .mapConcat(test -> Collections.nCopies(test.getCount(), test.getUrl()))
                .mapAsync(5, url -> {

                    return CompletableFuture.completedFuture(0);

                })
                .toMat(Sink.fold(0, Integer::sum), Keep.right());

    }



}
