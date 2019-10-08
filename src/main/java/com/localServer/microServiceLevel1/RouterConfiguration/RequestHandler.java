package com.localServer.microServiceLevel1.RouterConfiguration;


import com.localServer.microServiceLevel1.Repository.ResponseMessageContract;
import com.localServer.microServiceLevel1.models.ResponseMessage;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class RequestHandler {
    @Autowired
    ResponseMessageContract responseMessageContract;
    @Autowired
    Validator validator;


    public Mono < ServerResponse > getMessage(ServerRequest serverRequest) {
        return defaultReadResponse ( Flux.fromIterable ( responseMessageContract.getAllMessages ( ) ) );
    }

    public Mono < ServerResponse > getMessageById(ServerRequest serverRequest) {
        return defaultReadResponse ( Flux.just ( responseMessageContract.getMessage ( serverRequest.pathVariable ( "id" ).toString ( ) ) ) );
    }

    public Mono < ServerResponse > saveMessage(ServerRequest serverRequest) {
        Flux < ResponseMessage > flux = serverRequest
                .bodyToFlux ( ResponseMessage.class )
                .doOnNext ( this::validate )
                .map ( toWrite -> this.responseMessageContract.saveMessage ( toWrite ) );
        return defaultWriteResponse ( flux );
    }

    private void validate(ResponseMessage responseMessage) {
        Errors errors = new BeanPropertyBindingResult ( responseMessage , "ResponseMessage" );
        validator.validate ( responseMessage , errors );
        if (errors.hasErrors ( )) {
            throw new ServerWebInputException ( errors.toString ( ) );
        }
    }

    private static Mono < ServerResponse > defaultWriteResponse(Publisher < ResponseMessage > profiles) {
        return Mono
                .from ( profiles )
                .flatMap ( p -> ServerResponse
                        .created ( URI.create ( "/myapps/getMessage/" + p.getMid ( ) ) )
                        .contentType ( MediaType.APPLICATION_STREAM_JSON )
                        .build ( )
                );
    }

    private static Mono < ServerResponse > defaultReadResponse(Publisher < ResponseMessage > profiles) {
        return ServerResponse
                .ok ( )
                .contentType ( MediaType.APPLICATION_JSON_UTF8 )
                .body ( fromPublisher ( profiles , ResponseMessage.class ) );
    }

    public Mono < ServerResponse > deleteMessage(ServerRequest serverRequest) {
        return defaultReadResponse ( Flux.fromIterable ( responseMessageContract.deleteMessage ( serverRequest.pathVariable ( "id" ).toString ( ) ) ) );
    }


}
