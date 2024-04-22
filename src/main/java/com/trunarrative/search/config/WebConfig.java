package com.trunarrative.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${trunarrative.xapikey}")
    private String apiKey;

    @Value("${trunarrative.api.baseUrl}")
    private String baseUrl;


    @Bean
    public WebClient webClient() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add("x-api-key", apiKey);

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(h -> h.addAll(headers))
                .build();

        return webClient;
    }
}