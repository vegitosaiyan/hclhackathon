package com.hclhackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Spring Boot Application Class
 * This is the entry point for the HCL Hackathon API
 */
@SpringBootApplication
@EnableAsync
public class HclHackathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HclHackathonApplication.class, args);
    }
}
