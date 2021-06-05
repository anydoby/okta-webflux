package com.okta.developer.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class SearchController {

    @Value("${service.github}")
    private String apiUrl;

    @Autowired
    private WebClient webClient;


    @GetMapping(value = "/totalCount/{keyword}")
    public Mono<SearchCount> getSearchCountAuthorized(@PathVariable String keyword,
                                                      @RegisteredOAuth2AuthorizedClient( "github")OAuth2AuthorizedClient authorizedClient){
        return this.webClient.get().uri(apiUrl, uriBuilder -> uriBuilder
                .path("/search/code")
                .queryParam("q", keyword).build())
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(SearchCount.class);
    }

}