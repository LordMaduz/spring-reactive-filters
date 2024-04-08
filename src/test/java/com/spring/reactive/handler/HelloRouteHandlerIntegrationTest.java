package com.spring.reactive.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.spring.reactive.router.HelloRouter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HelloRouter.class, HelloRouterHandler.class})
@WebFluxTest
public class HelloRouteHandlerIntegrationTest {

    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testHelloFunctionalEndpoint() {
        EntityExchangeResult<String> result = webTestClient.get()
            .uri("/hello-functional")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult();

        final String body = "Hello From: FUNCTIONAL-USER";
        assertEquals(result.getResponseBody(), body);

    }
}
