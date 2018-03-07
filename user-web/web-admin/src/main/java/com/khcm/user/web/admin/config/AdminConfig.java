package com.khcm.user.web.admin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 自定义属性配置文件
 * Created by yangwb on 2017/12/6.
 */
@Data
@Configuration
@PropertySource("classpath:config/user-admin.yml")
@ConfigurationProperties(prefix = "user.admin", ignoreUnknownFields = false)
public class AdminConfig {

    @Value("${appCode}")
    private String appCode;

}
