package com.energizor.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnergizorRestapiApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(EnergizorRestapiApplication.class, args);
    }

}
