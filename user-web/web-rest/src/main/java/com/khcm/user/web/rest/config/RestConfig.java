package com.khcm.user.web.rest.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:config/user-rest.yml")
@ConfigurationProperties(prefix = "user.rest", ignoreUnknownFields = false)
public class RestConfig {

    @Value("${appCode}")
    private String appCode;

}
