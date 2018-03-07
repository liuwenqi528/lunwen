package com.khcm.user.web.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用程序启动类
 *
 * @author wangtao
 * @date 2017/9/15
 */
@ComponentScan(basePackages = {"com.khcm.user"})
@SpringBootApplication
public class AdminApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }

}
