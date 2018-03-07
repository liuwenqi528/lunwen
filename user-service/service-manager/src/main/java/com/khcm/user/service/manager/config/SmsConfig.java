package com.khcm.user.service.manager.config;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SmsConfig {

    @Value("${sms.tencent.appId}")
    private int appId;

    @Value("${sms.tencent.appKey}")
    private String appKey;

    @Bean
    public SmsSingleSender smsSingleSender() {
        try {
            return new SmsSingleSender(appId, appKey);
        } catch (Exception ex) {
           log.error("initial sms single sender error", ex);
           return null;
        }
    }

    @Bean
    public SmsMultiSender smsMultiSender() {
        try {
            return new SmsMultiSender(appId, appKey);
        } catch (Exception ex) {
            log.error("initial sms multi sender error", ex);
            return null;
        }
    }

}
