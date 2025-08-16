package com.g41.trashsmart_server.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FastApiConfig {
    @Bean
    public WebClient fastApiWebClient(@Value("${fast.api.url}") String fastApiUrl) {
        return WebClient.builder()
                .baseUrl(fastApiUrl)
                .build();
    }
}
