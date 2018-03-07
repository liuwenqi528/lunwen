package com.khcm.user.dao.config;

import com.khcm.user.dao.repository.BaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.khcm.user.dao.entity")
@EnableJpaRepositories(basePackages = "com.khcm.user.dao.repository.master", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JpaMasterConfig {

}
