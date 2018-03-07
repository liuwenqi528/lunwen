package com.khcm.user.web.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yangwb on 2017/12/6.
 */
@ComponentScan(basePackages = {"com.khcm.user"})
@SpringBootApplication
public class RestApp {

    public static void main(String[] args) {
        SpringApplication.run(RestApp.class, args);
    }

}
