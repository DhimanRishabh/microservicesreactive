package com.demo.test.RouterConfiguration;


import com.demo.test.Repository.ResponseMessageContract;
import com.demo.test.models.ResponseMessage;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.time.Duration;
import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class RequestHandler {
    @Autowired
    ResponseMessageContract responseMessageContract;

    public Mono<ServerResponse> getMessage(ServerRequest serverRequest) {
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(fromPublisher(Flux.fromIterable(responseMessageContract.getAllMessages())
                        .log(), ResponseMessage.class));
    }

    public Mono<ServerResponse> getMessageById(ServerRequest serverRequest) {
        return ok()
        .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(fromPublisher(Flux.just(responseMessageContract.getMessage(serverRequest.pathVariable("id").toString()))
                        .log(), ResponseMessage.class));
    }

    public Mono<ServerResponse> saveMessage( ServerRequest serverRequest ) {
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(fromPublisher(serverRequest.bodyToFlux(ResponseMessage.class).map(toWrite -> responseMessageContract.saveMessage(toWrite))
                        .log(), ResponseMessage.class));

    }
    public Mono<ServerResponse> deleteMessage( ServerRequest serverRequest ) {
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(fromPublisher(Flux.fromIterable( responseMessageContract.deleteMessage(serverRequest.pathVariable("id").toString()))
                        .log(), ResponseMessage.class));

    }


}
