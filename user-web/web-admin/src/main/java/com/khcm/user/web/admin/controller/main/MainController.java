package com.khcm.user.web.admin.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录后首页管理
 * Created by liuwenqi on 2018/1/20.
 */
@Controller
@RequestMapping("/index/main")
public class MainController {

    public static final String BASE_PATH = "business/";


    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "main";
    }


}

