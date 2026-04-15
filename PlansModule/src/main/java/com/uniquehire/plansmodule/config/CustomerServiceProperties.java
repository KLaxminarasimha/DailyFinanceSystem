package com.uniquehire.plansmodule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "customer.service")
public class CustomerServiceProperties {
    private String baseUrl;
    private String incomeEndpoint;    
}
