package com.uniquehire.TransactionAndReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class  TransactionReportService{

    public static void main(String[] args) {

        SpringApplication.run(TransactionReportService.class, args);
    }

}
