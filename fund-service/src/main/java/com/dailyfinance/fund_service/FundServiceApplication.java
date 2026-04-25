package com.dailyfinance.fund_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FundServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FundServiceApplication.class, args);
	}
}
