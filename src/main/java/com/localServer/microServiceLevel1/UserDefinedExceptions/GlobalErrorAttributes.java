package com.localServer.microServiceLevel1.UserDefinedExceptions;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map < String, Object > getErrorAttributes(ServerRequest request , boolean includeStackTrace) {


        if (getError ( request ) instanceof GlobalException) {
            Map < String, Object > map = super.getErrorAttributes ( request , false );
            GlobalException ex = (GlobalException) getError ( request );
            map.put ( "exception" , ex.getClass ( ).getSimpleName ( ) );
            map.put ( "message" , ex.getMessage ( ) );
            map.put ( "status" , ex.getStatus ( ).value ( ) );
            map.put ( "error" , ex.getStatus ( ).getReasonPhrase ( ) );

            return map;
        }
        Map < String, Object > map = super.getErrorAttributes ( request , true );
        Throwable error = getError ( request );
        map.put ( "exception" , "SystemException" );
        map.put ( "message" , "System Error , Check logs!" );
        map.put ( "status" , "500" );
        map.put ( "error" , error.getMessage ( ) );
        return map;
    }
}