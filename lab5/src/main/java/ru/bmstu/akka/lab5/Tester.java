package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import ru.bmstu.akka.lab5.ResultURL;
import ru.bmstu.akka.lab5.TestURL;
import ru.bmstu.akka.lab5.TestUrlMsg;
import scala.compat.java8.FutureConverters;


import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class Tester {
    private ActorRef actorRef;
    private ActorMaterializer materializer;
    private AsyncHttpClient asyncHttpClient;

    public Tester(AsyncHttpClient asyncHttpClient, ActorSystem system, ActorMaterializer materializer) {
        this.materializer = materializer;
        this.asyncHttpClient = asyncHttpClient;
        this.actorRef = system.actorOf(Props.create(StoreActor.class));
    }
     public Flow<HttpRequest, HttpResponse, NotUsed> createRoute(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(this::request)
                .mapAsync(1, this::processTest)
                .map(this::)
    }

    private TestURL request(HttpRequest httpRequest) {
        Query query = httpRequest.getUri().query();
        Optional<String> testUrl = query.get("testUrl");
        Optional<String> count = query.get("count");
        return new TestURL(testUrl.get(), Integer.parseInt(count.get()));
    }

    private CompletionStage<ResultURL> processTest(TestURL testURL) {
       return FutureConverters.toJava(Patterns.ask(this.actorRef, testURL, 5000))
               .thenApply(r -> (TestUrlMsg)r)
               .thenCompose(res -> {
                   Optional<ResultURL> resultURL = res.getResultURL();
                   if (resultURL.isPresent()) {
                       return CompletableFuture.completedFuture(resultURL.get());

                   } return startTest(testURL);

                       });



    }

    private CompletionStage<ResultURL> startTest(TestURL testURL) {
        final Sink<TestURL, CompletionStage<Integer>> sink = createSink();
        return Source.from(Collections.singletonList(testURL))
                .toMat(sink, Keep.right())
                .run(materializer)
                .thenApply(sum -> {
                    ResultURL resultURL = new ResultURL(testURL, sum/testURL.getCount());
                    actorRef.tell(resultURL, ActorRef.noSender());
                    return resultURL;
                });
    }

    private  Sink<TestURL, CompletionStage<Integer>> createSink() {
        //Flow.<TestURL>create()
        Flow.of(TestURL.class)
                .mapConcat(test -> Collections.nCopies(test.getCount(), test.getUrl()))
                .mapAsync(5, this::getTimeResource)
                .toMat(Sink.fold(0, Integer::sum), Keep.right());

    }

    private HttpResponse complerePequest(ResultURL resultURL) throws JsonProcessingException {
        actorRef.tell(resultURL, ActorRef.noSender());
        return  HttpResponse.create()
                .withStatus(StatusCodes.OK)
                .withEntity(resultURL.getTime().toString());
                //.withEntity(ContentTypes.APPLICATION_JSON, ByteString.fromString(
                  //      new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resultURL)
                //));

    }

    private CompletionStage<Integer> getTimeResource(String url) {
        Instant start = Instant.now();
       return asyncHttpClient.
               prepareGet(url).execute()
               .toCompletableFuture()
               .thenCompose(r -> CompletableFuture.completedFuture(
                       Duration.between(start, Instant.now().getNano())
               ))
    }



}
