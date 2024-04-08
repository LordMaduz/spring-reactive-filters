package com.spring.reactive.router;

import com.spring.reactive.filters.TransformRequestHandlerFilterFunction;
import com.spring.reactive.handler.HelloRouterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(HelloRouterHandler routerHandler) {
        return route(GET("/hello-functional"), routerHandler::hello)
            .filter(new TransformRequestHandlerFilterFunction());
    }

}
