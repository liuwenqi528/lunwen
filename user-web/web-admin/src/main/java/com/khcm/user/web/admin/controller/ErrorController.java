package com.khcm.user.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

    /**
     * 页面异常
     *
     * @return
     */
    @RequestMapping(value = "/pageError", method = RequestMethod.GET)
    public String pageError() {
        return "pageError";
    }

    /**
     * 页面不存在
     *
     * @return
     */
    @RequestMapping(value = "/pageNotFound", method = RequestMethod.GET)
    public String pageNotFound() {
        return "pageNotFound";
    }

    /**
     * 页面未授权
     *
     * @return
     */
    @RequestMapping(value = "/pageUnauth", method = RequestMethod.GET)
    public String pageUnauth() {
        return "pageUnauth";
    }
}
