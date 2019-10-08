package com.localServer.microServiceLevel1.RouterConfiguration;


import com.localServer.microServiceLevel1.beanValidators.ResponseMessageValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouterFunction < ServerResponse > roues(RequestHandler handler) {
        return RouterFunctions.route ( )
                .path ( "/myapps" , builder -> builder
                        .GET ( "/" , accept ( MediaType.APPLICATION_JSON ) , handler::getMessage )
                        .GET ( "/getMessage/{id}" , accept ( MediaType.APPLICATION_JSON ) , handler::getMessageById )
                        .PUT ( "/saveMessage" , accept ( MediaType.APPLICATION_JSON ) , handler::saveMessage )
                        .DELETE ( "/deleteMessage/{id}" , accept ( MediaType.APPLICATION_JSON ) , handler::deleteMessage ) )
                .build ( );
    }


}
