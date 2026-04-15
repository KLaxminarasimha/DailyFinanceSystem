package com.uniquehire.plansmodule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "customer.service")
public class CustomerServiceProperties {
    private String baseUrl;
    private String incomeEndpoint;
}
