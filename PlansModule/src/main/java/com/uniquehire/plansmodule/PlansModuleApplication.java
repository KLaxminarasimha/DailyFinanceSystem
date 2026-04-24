package com.uniquehire.plansmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlansModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlansModuleApplication.class, args);
    }
}