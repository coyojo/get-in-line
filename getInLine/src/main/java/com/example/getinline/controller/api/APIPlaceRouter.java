package com.example.getinline.controller.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

//@Configuration
public class APIPlaceRouter {
    //APIPlaceController를 함수형 으로 바꾼 내용!
   // @Bean
    public RouterFunction<ServerResponse> placeRouter(APIPlaceHandler apiPlaceHandler){
        return route().nest(path("/api/places"),builder -> builder
                .GET("", apiPlaceHandler::getPlaces)
                .POST("", apiPlaceHandler::createPlace)
                .GET("/{placeId}", apiPlaceHandler::getPlace)
                .PUT("/{placeId}", apiPlaceHandler::modifyPlace)
                .DELETE("/{placeId}", apiPlaceHandler::removePlace)
        ).build();
    }

    /* APIPlaceHandler로 분리하여 주입하기 전 코드
    @Bean
    public RouterFunction<ServerResponse> placeRouter(){
        return route().nest(path("/api/places"),builder -> builder
                .GET("", req -> ServerResponse.ok().body(List.of("place1","place2")))
                .POST("", req -> ServerResponse.ok().body(true))
                .GET("/{placeId}", req -> ServerResponse.ok().body("place"+req.pathVariable("palcdId")))
                .PUT("/{placeId}", req -> ServerResponse.ok().body(true))
                .DELETE("/{placeId}", req -> ServerResponse.ok().body(true))
        ).build();
    }
    */

}
