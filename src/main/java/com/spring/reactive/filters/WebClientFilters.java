package com.spring.reactive.filters;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public final class WebClientFilters {


    public static ExchangeFilterFunction transformRequestHeaders(final MultiValueMap<String, String> changedMap) {
        return (request, next) -> {
            final ClientRequest clientRequest = ClientRequest.from(request)
                .header("spanId", "SPAN-ID")
                .build();
            changedMap.addAll(clientRequest.headers());
            return next.exchange(clientRequest);
        };
    }

    public static ExchangeFilterFunction transformResponseHeaders() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            final ClientResponse response = clientResponse.mutate()
                .body("Response body got changed")
                .build();
            return Mono.just(response);
        });
    }

    public static ExchangeFilterFunction transformRequestHeaders() {
        return (request, next) -> {
            final ClientRequest clientRequest = ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("userName", "password"))
                .header("spanId", "SPAN-ID")
                .build();
            return next.exchange(clientRequest);
        };
    }

    public static ExchangeFilterFunction logClientRequest() {
        return (clientRequest, exchangeFunction) -> {
            log.info("Client Request: {} {}", clientRequest.method(),clientRequest.url().getPath());
            clientRequest.headers()
                .forEach((name, values) -> values.forEach(value -> log.info("{}={}\n", name, value)));
            return exchangeFunction.exchange(clientRequest);
        };
    }

    public static ExchangeFilterFunction logClientResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Client Response Status: {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientResponse);
        });
    }
}
