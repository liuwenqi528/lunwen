package com.khcm.user.web.admin.config;

import com.khcm.user.web.admin.shiro.AdminCasRealm;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class Pac4jConfig {

    @Value("${shiro.cas.preffixUrl}")
    private String preffixUrl;

    @Value("${shiro.cas.loginUrl}")
    private String loginUrl;

    @Value("${shiro.cas.logoutUrl}")
    private String logoutUrl;

    @Value("${shiro.cas.serviceUrl}")
    private String serviceUrl;

    @Value("${shiro.cas.callbackUrl}")
    private String callbackUrl;

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/callback", "casCallbackFilter");
        filterChainDefinitionMap.put("/logout", "casLogoutFilter");
        filterChainDefinitionMap.put("/**", "casSecurityFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("casSecurityFilter", casSecurityFilter());
        filters.put("casCallbackFilter", casCallbackFilter());
        filters.put("casLogoutFilter", casLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setRealm(adminCasRealm());
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    @Bean
    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    @Bean
    public AdminCasRealm adminCasRealm() {
        return new AdminCasRealm();
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManager(ehCacheCacheManager.getCacheManager());
        return cacheManager;
    }

    @Bean
    public Filter casSecurityFilter() {
        SecurityFilter filter = new SecurityFilter();
        filter.setClients("cas");
        filter.setConfig(config());
        return filter;
    }

    @Bean
    public Filter casCallbackFilter() {
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config());
        return callbackFilter;
    }

    @Bean
    public Filter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config());
        logoutFilter.setDefaultUrl(logoutUrl);
        logoutFilter.setLocalLogout(true);
        return logoutFilter;
    }

    @Bean
    protected Config config() {
        Config config = new Config();
        config.setClients(clients());
        return config;
    }

    @Bean
    public Clients clients() {
        Clients clients = new Clients();
        clients.setClients(casClient());
        return clients;
    }

    @Bean
    public CasClient casClient() {
        CasClient casClient = new CasClient();
        casClient.setName("cas");
        casClient.setConfiguration(casConfiguration());
        casClient.setCallbackUrl(callbackUrl);
        return casClient;
    }

    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration(loginUrl);
        casConfiguration.setProtocol(CasProtocol.CAS30);
        casConfiguration.setPrefixUrl(preffixUrl);
        return casConfiguration;
    }

    @Bean
    public FilterRegistrationBean casSecurityFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(casSecurityFilter());
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public FilterRegistrationBean casCallbackFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(casCallbackFilter());
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public FilterRegistrationBean casLogoutFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(casLogoutFilter());
        registration.setEnabled(false);
        return registration;
    }

}
