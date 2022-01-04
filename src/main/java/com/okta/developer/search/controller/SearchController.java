package com.okta.developer.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SearchController {

    @GetMapping(value = "/hello")
    public Mono<String> getSearchCountAuthorized() {
        return Mono.just("hello");
    }

}
