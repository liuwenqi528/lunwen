package com.khcm.user.service.impl.email;

import com.khcm.user.common.exception.ServiceException;
import com.khcm.user.service.api.email.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_FTL = "mail.ftl";

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    Configuration configuration;

    @Async
    @Override
    public void sendMail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    @Async
    @Override
    public void sendMail(String to, String title, Map<String, Object> content) {
        sendMail(to, title, content, null);
    }

    @Async
    @Override
    public void sendMail(String to, String title, String content, Map<String, File> attachments) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);

            if (MapUtils.isNotEmpty(attachments)) {
                for (String fileName : attachments.keySet()) {
                    helper.addAttachment(fileName, attachments.get(fileName));
                }
            }
        } catch (MessagingException ex) {
            throw new ServiceException("邮件发送失败：" + ex.getMessage(), ex);
        }

        mailSender.send(message);
    }

    @Async
    @Override
    public void sendMail(String to, String title, Map<String, Object> content, Map<String, File> attachments) {

        try {
            Template ftl = configuration.getTemplate(EMAIL_FTL);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(ftl, content);

            sendMail(to, title, text, attachments);

        } catch (IOException | TemplateException ex) {
            throw new ServiceException("邮件发送失败：" + ex.getMessage(), ex);
        }

    }

}
