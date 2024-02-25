package com.energizor.restapi;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class EnergizorRestapiApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(EnergizorRestapiApplication.class, args);
    }

    @PostConstruct
    public void init(){
        // UTC 시간대 설정
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("Spring boot application running in UTC timezone :" + new Date());
    }
}
