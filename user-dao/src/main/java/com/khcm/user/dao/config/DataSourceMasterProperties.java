package com.khcm.user.dao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Created by wangtao on 2017/10/25.
 */
@Data
@ConfigurationProperties("spring.datasource.master")
public class DataSourceMasterProperties {

    //数据库配置
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    //连接池配置
    private Integer initialSize = 5;
    private Integer maxActive = 20;
    private Integer minIdle = 5;
    private Long minEvictableIdleTimeMillis = 300000L;
    private Long maxEvictableIdleTimeMillis;
    private Long maxWait = 60000L; //获取连接等待超时的时间
    private Long timeBetweenEvictionRunsMillis = 60000L; //间隔多久检测一次需要关闭的空闲连接，单位是毫秒

    //检测配置
    private Boolean testWhileIdle = true;
    private Boolean testOnBorrow;
    private String validationQuery = "SELECT 1";

    //监控配置
    private Boolean useGlobalDataSourceStat; //是否合并多个DruidDataSource的监控数据
    private Integer maxSize; //统计SQL最大量
    private String filters; //配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    private Long timeBetweenLogStatsMillis;
    private Boolean clearFiltersEnable;
    private Boolean resetStatEnable;
    private Integer notFullTimeoutRetryCount;
    private Integer maxWaitThreadCount;
    private Boolean failFast;
    private Boolean phyTimeoutMillis;

    //游标缓存配置
    private Boolean poolPreparedStatements = true;
    private Integer maxPoolPreparedStatementPerConnectionSize = 20; //指定每个连接上PSCache的大小

    //通过connectProperties属性来打开mergeSql功能,慢SQL记录
    private Properties connectionProperties = new Properties() {{
        put("druid.stat.mergeSql", "true");
        put("druid.stat.slowSqlMillis", "5000");
    }};

    public Properties toProperties() {
        Properties properties = new Properties();

        notNullAdd(properties, "driverClassName", this.driverClassName);
        notNullAdd(properties, "url", this.url);
        notNullAdd(properties, "username", this.username);
        notNullAdd(properties, "password", this.password);

        notNullAdd(properties, "initialSize", this.initialSize);
        notNullAdd(properties, "minIdle", this.minIdle);
        notNullAdd(properties, "maxActive", this.maxActive);
        notNullAdd(properties, "minEvictableIdleTimeMillis", this.minEvictableIdleTimeMillis);
        notNullAdd(properties, "maxEvictableIdleTimeMillis", this.maxEvictableIdleTimeMillis);
        notNullAdd(properties, "maxWait", this.maxWait);
        notNullAdd(properties, "timeBetweenLogStatsMillis", this.timeBetweenLogStatsMillis);

        notNullAdd(properties, "testWhileIdle", this.testWhileIdle);
        notNullAdd(properties, "testOnBorrow", this.testOnBorrow);
        notNullAdd(properties, "validationQuery", this.validationQuery);

        notNullAdd(properties, "useGlobalDataSourceStat", this.useGlobalDataSourceStat);
        notNullAdd(properties, "stat.sql.MaxSize", this.maxSize);
        notNullAdd(properties, "filters", this.filters);
        notNullAdd(properties, "timeBetweenEvictionRunsMillis", this.timeBetweenEvictionRunsMillis);
        notNullAdd(properties, "clearFiltersEnable", this.clearFiltersEnable);
        notNullAdd(properties, "resetStatEnable", this.resetStatEnable);
        notNullAdd(properties, "notFullTimeoutRetryCount", this.notFullTimeoutRetryCount);
        notNullAdd(properties, "maxWaitThreadCount", this.maxWaitThreadCount);
        notNullAdd(properties, "failFast", this.failFast);
        notNullAdd(properties, "phyTimeoutMillis", this.phyTimeoutMillis);

        notNullAdd(properties, "poolPreparedStatements", this.poolPreparedStatements);
        notNullAdd(properties, "maxPoolPreparedStatementPerConnectionSize", this.maxPoolPreparedStatementPerConnectionSize);

        return properties;
    }

    private void notNullAdd(Properties properties, String key, Object value) {
        if (value != null) {
            properties.setProperty("druid." + key, value.toString());
        }
    }

}
