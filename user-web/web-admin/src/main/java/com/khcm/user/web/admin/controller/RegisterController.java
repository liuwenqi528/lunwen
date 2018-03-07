package com.khcm.user.web.admin.controller;

import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.param.business.system.UserParam;
import com.khcm.user.web.admin.model.mapper.system.UserDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.UserPM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.utils.VMUtils;
import com.khcm.user.web.common.utils.WebUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public String index() {
        return "register";
    }


    @RequiresPermissions("register")
    @RequestMapping("/doRegister")
    @ResponseBody
    public ResultVM doRegister(HttpServletRequest request, UserPM userPM) {
        UserParam userParam = UserDTOMapper.MAPPER.pmToParam(userPM);
        userParam.setAdmin(false);
        userParam.setRegisterIp(WebUtils.getIpAddr(request));
        userService.saveOrUpdate(userParam);
        return VMUtils.resultSuccess();
    }

    @RequestMapping("/success")
    public String success() {
        return "registerSuccess";
    }

}
