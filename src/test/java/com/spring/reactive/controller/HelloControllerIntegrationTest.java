package com.spring.reactive.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = HelloController.class)
public class HelloControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHelloAnnotatedEndpoint() {
        EntityExchangeResult<String> result = webTestClient.get()
            .uri("/hello-annotated")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult();

        final String body = "Hello From: USER";
        assertEquals(result.getResponseBody(), body);

    }
}
