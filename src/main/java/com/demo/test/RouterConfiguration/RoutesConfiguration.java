package com.demo.test.RouterConfiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouterFunction<ServerResponse> roues(RequestHandler handler){
        return RouterFunctions.route(GET("/myapps/getAllMessages").and(accept(MediaType.APPLICATION_JSON)),handler::getMessage)
                .andRoute(GET("/myapps/getMessage/{id}").and(accept(MediaType.APPLICATION_JSON)),handler::getMessageById)
                .andRoute(PUT("/myapps/saveMessage").and(accept(MediaType.APPLICATION_JSON)),handler::saveMessage)
                .andRoute(DELETE("/myapps/deleteMessage/{id}").and(accept(MediaType.APPLICATION_JSON)),handler::deleteMessage)
                ;
    }
}
