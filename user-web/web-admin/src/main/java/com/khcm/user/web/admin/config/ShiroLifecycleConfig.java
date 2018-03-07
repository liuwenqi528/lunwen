package com.khcm.user.web.admin.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangwb on 2017/12/29.
 */
@Configuration
public class ShiroLifecycleConfig {

    /**
     * 执行Shiro生命周期方法
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
