package com.spring.reactive.filters;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest
public class WebClientFilterUnitTest {

    @RegisterExtension
    static WireMockExtension extension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort()
            .dynamicHttpsPort())
        .build();

    @Test
    void testLogRequestFilter() {
        extension.stubFor(post("/test").willReturn(aResponse().withStatus(200)
            .withBody("SUCCESS")));

        WebClient webClient = WebClient.builder()
            .filter(WebClientFilters.logClientRequest())
            .build();

        final String response = webClient.post()
            .uri("http://localhost:" + extension.getPort() + "/test")
            .header("request-id", UUID.randomUUID().toString())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        Assertions.assertEquals("SUCCESS", response);
    }

    @Test
    void testLogResponseFilter() {
        extension.stubFor(post("/test").willReturn(aResponse().withStatus(200)
            .withBody("SUCCESS")));

        WebClient webClient = WebClient.builder()
            .filter(WebClientFilters.logClientResponse())
            .build();

        final String response = webClient.post()
            .uri("http://localhost:" + extension.getPort() + "/test")
            .header("request-id", UUID.randomUUID().toString())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        Assertions.assertEquals("SUCCESS", response);
    }
}
