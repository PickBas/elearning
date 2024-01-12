package com.saied.elearning.mainmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "com.saied.elearning.mainmicroservice",
        "com.saied.elearning.acmq"
    }
)
public class MainMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainMicroserviceApplication.class, args);
    }
}
