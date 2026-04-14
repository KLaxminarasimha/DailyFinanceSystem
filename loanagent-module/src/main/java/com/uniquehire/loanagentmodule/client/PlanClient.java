package com.uniquehire.loanagentmodule.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PlanClient {

    private final RestTemplate restTemplate;

    @Value("${services.plan-service.url}")
    private String planServiceUrl;

    public Object getPlan(Long planId) {

        return restTemplate.getForObject(
                planServiceUrl + "/" + planId,
                Object.class
        );
    }
}
