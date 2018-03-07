package com.khcm.user.web.admin.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.khcm.user.common.constant.Constants;
import com.khcm.user.web.admin.dialect.AminDialect;
import com.khcm.user.web.admin.dialect.ShiroExpression;
import com.khcm.user.web.admin.interceptor.AdminInterceptor;
import com.khcm.user.web.admin.interceptor.OperationLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

/**
 * Spring MVC 配置
 *
 * @author wangtao
 * @date 2017/8/25
 */
@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /** 使用阿里 FastJson 作为JSON MessageConverter */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName(Constants.DEFAULT_ENCODE));
        converters.add(converter);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return resolver;
    }

    @Bean
    public AminDialect aminDialect() {
        return new AminDialect();
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    @ConditionalOnMissingBean
    public ShiroExpression shiroExpression() {
        return new ShiroExpression();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(operationLogInterceptor()).addPathPatterns("/*/*/*");
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Bean
    public OperationLogInterceptor operationLogInterceptor() {
        return new OperationLogInterceptor();
    }
}
