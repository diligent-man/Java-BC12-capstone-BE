package com.ndt.capstone.config;

import java.time.Duration;


import org.springframework.web.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;


@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .requestFactory(clientHttpRequestFactory())
            .build();
    }


    private ClientHttpRequestFactory clientHttpRequestFactory() {
        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(10000));
        factory.setReadTimeout(Duration.ofMillis(10000));
        return factory;
    }
}
