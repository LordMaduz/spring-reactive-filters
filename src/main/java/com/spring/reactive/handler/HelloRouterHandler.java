package com.spring.reactive.handler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class HelloRouterHandler {

    public Mono<ServerResponse> hello(final ServerRequest serverRequest) {

        final String userGroup = serverRequest.headers().firstHeader("user-group");
        assert userGroup != null;
        final Mono<String> body = Mono.just("Hello From: ".concat(userGroup));
        return ok().body(body, String.class);
    }


}
