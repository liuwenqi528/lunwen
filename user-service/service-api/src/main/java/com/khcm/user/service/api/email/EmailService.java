package com.khcm.user.service.api.email;

import java.io.File;
import java.util.Map;

public interface EmailService {

    void sendMail(String to, String title, String content);

    void sendMail(String to, String title, Map<String, Object> content);

    void sendMail(String to, String title, String content, Map<String, File> attachments);

    void sendMail(String to, String title, Map<String, Object> content, Map<String, File> attachments);

}
