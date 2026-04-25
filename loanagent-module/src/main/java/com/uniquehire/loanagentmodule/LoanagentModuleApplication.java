package com.uniquehire.loanagentmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LoanagentModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanagentModuleApplication.class, args);
    }
}
