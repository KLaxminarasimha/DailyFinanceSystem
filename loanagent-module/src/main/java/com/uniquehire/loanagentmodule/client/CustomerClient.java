package com.uniquehire.loanagentmodule.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${services.customer-service.url}")
    private String customerServiceUrl;

    public Object getCustomer(Long customerId) {

        return restTemplate.getForObject(
                customerServiceUrl + "/" + customerId,
                Object.class
        );
    }
}
