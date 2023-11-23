package com.joinus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {
    "classpath:google.properties",
    "classpath:dbconnect.properties",
    "classpath:kakao.properties",
    "classpath:mail.properties",
})
public class JoinUsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoinUsApplication.class, args);
    }

}
