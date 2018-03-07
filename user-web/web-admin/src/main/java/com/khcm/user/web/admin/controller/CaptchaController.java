package com.khcm.user.web.admin.controller;

import com.khcm.user.web.admin.kaptcha.AdminKaptcha;
import com.khcm.user.web.admin.shiro.AdminFormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by yangwb on 2018/1/3.
 */
@Controller
public class CaptchaController {

    @Autowired
    AdminKaptcha adminKaptcha;

    @RequestMapping("/images/captcha.svl")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        String createText = adminKaptcha.createText();
        request.getSession().setAttribute(AdminFormAuthenticationFilter.CAPTCHA_SESSION_KEY, createText);
        BufferedImage challenge = adminKaptcha.createImage(createText);
        ImageIO.write(challenge, "jpg", jpegOutputStream);

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        out.write(jpegOutputStream.toByteArray());
        out.flush();
        out.close();
    }
}
