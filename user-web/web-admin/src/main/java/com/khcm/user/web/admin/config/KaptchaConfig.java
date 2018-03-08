package com.khcm.user.web.admin.config;

import com.google.code.kaptcha.util.Config;
import com.khcm.user.web.admin.kaptcha.AdminKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.util.Properties;

/**
 * Created by yangwb on 2018/1/3.
 */
@Slf4j
@Configuration
public class KaptchaConfig {

    @Bean
    public AdminKaptcha  getAdminKaptcha() {
        AdminKaptcha  adminKaptcha = new AdminKaptcha ();

        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "36");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.noise.impl","com.khcm.user.web.admin.kaptcha.AdminNoise");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        adminKaptcha.setConfig(config);

        return adminKaptcha;
    }
}
