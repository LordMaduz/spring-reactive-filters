package com.spring.reactive.filters;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class TransformRequestHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> handlerFunction) {

        ServerRequest serverRequest = ServerRequest.from(request)
            .header("user-group", "FUNCTIONAL-USER")
            .build();
        return handlerFunction.handle(serverRequest);
    }
}
