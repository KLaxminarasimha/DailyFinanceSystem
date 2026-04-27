package com.uniquehire.paymentservice.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced   // 🔥 VERY IMPORTANT
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
