package com.example.reactiveweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
public class HomeControllerSliceTest {
    @Autowired
    private WebTestClient client;

    @Test
    void homePage(){

    }
}
