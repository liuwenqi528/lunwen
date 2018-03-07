package com.khcm.user.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Druid数据源配置
 *
 * @author wangtao on 2017/10/25
 */
@Configuration
@EnableConfigurationProperties(DataSourceMasterProperties.class)
public class DataSourceMasterConfig {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceMasterProperties druidProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(druidProperties.toProperties());
        druidDataSource.setConnectProperties(druidProperties.getConnectionProperties());
        return druidDataSource;
    }

}
