package com.uniquehire.plansmodule;

import com.uniquehire.plansmodule.config.CustomerServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CustomerServiceProperties.class)

public class PlansModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlansModuleApplication.class, args);
    }

}
