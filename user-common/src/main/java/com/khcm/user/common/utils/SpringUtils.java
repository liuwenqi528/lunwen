package com.khcm.user.common.utils;

import com.khcm.user.common.exception.SystemException;
import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Spring 的 ApplicationContext 持有者
 * 可以用静态方法获取 spring 容器中的 Bean
 *
 * @author wangtao
 * @date 2017/8/16
 */
@Component
public class SpringUtils implements ApplicationContextAware, ApplicationEventPublisherAware {

    private static ApplicationContext applicationContext;
    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        SpringUtils.publisher = publisher;
    }

    /**
     * 获取实例
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 获取实例
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取国际化消息
     * @param key
     * @param variables
     * @return
     */
    public static String getMessage(String key, String...variables) {
        assertApplicationContext();
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        return messageSource.getMessage(key, variables, LocaleContextHolder.getLocale());
    }

    /**
     * 发布Spring事件
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        publisher.publishEvent(event);
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new SystemException("applicaitonContext属性为null,请检查是否注入了SpringUtils!");
        }
    }

}
