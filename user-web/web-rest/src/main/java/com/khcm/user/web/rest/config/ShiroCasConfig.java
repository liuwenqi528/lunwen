package com.khcm.user.web.rest.config;

import com.khcm.user.web.rest.shiro.RestCasRealm;
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
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
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
public class ShiroCasConfig {

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

    @Value("${shiro.jwt.secret}")
    private String secret;

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
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
        securityManager.setRealm(restCasRealm());
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    @Bean
    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    @Bean
    public RestCasRealm restCasRealm() {
        return new RestCasRealm();
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
        filter.setClients("rest,jwt");
        filter.setConfig(casConfig());
        return filter;
    }

    @Bean
    public Filter casCallbackFilter() {
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(casConfig());
        return callbackFilter;
    }

    @Bean
    public Filter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(casConfig());
        logoutFilter.setDefaultUrl(logoutUrl);
        return logoutFilter;
    }

    @Bean
    protected Config casConfig() {
        Config config = new Config();
        config.setClients(clients());
        return config;
    }

    /**================ CLIENT CONFIG START ================*/

    @Bean
    public Clients clients() {
        Clients clients = new Clients();
        clients.setClients(casRestFormClient(), parameterClient());
        return clients;
    }

    @Bean
    protected CasRestFormClient casRestFormClient() {
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setName("rest");
        casRestFormClient.setConfiguration(casConfiguration());
        return casRestFormClient;
    }

    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration(loginUrl);
        casConfiguration.setProtocol(CasProtocol.CAS30);
        casConfiguration.setPrefixUrl(preffixUrl);
        return casConfiguration;
    }

    @Bean
    protected JwtGenerator jwtGenerator() {
        return new JwtGenerator(new SecretSignatureConfiguration(secret), new SecretEncryptionConfiguration(secret));
    }

    @Bean
    public ParameterClient parameterClient() {
        ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
        parameterClient.setName("jwt");
        parameterClient.setSupportGetRequest(true);
        return parameterClient;
    }

    @Bean
    protected JwtAuthenticator jwtAuthenticator() {
        JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration(secret));
        jwtAuthenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(secret));
        return jwtAuthenticator;
    }

    /**================ CLIENT CONFIG END ================*/

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
