package com.spring.reactive.controller;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.spring.reactive.filters.WebClientFilters;

import io.netty.handler.logging.LogLevel;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@RestController
public class HelloController {

    @GetMapping(value = "/hello-annotated")
    public Mono<String> trace(@RequestHeader(name = "user-group") final String userGroup) {
        return Mono.just("Hello From: ".concat(userGroup));
    }

    @GetMapping("/hello-webclient")
    public Mono<String> webClientTest() {

        HttpClient httpClient = HttpClient
            .create()
            .wiretap("reactor.netty.http.client.HttpClient",
                LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

        WebClient webClient = WebClient.builder()
            .filter(WebClientFilters.transformRequestHeaders())
            .filter(WebClientFilters.transformResponseHeaders())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        return webClient.get()
            .uri("http://localhost:8080/hello-annotated")
            .retrieve()
            .bodyToMono(String.class);
    }
}
