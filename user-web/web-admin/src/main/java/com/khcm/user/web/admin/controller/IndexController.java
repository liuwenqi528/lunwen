package com.khcm.user.web.admin.controller;

import com.khcm.user.service.api.system.AuthorizationService;
import com.khcm.user.service.dto.business.system.ModuleDTO;
import com.khcm.user.web.admin.config.AdminConfig;
import com.khcm.user.web.admin.model.mapper.system.ModuleDTOMapper;
import com.khcm.user.web.admin.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 管理系统首页 Controller
 *
 * @author wangtao
 * @date 2017/9/23
 */
@Controller
public class IndexController{

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AdminConfig adminConfig;

    /**
     * 登录页面主页
     * @return
     */
    @GetMapping({"/","/index"})
    public String index(ModelMap modelMap) {
        List<ModuleDTO> moduleList = authorizationService.getAppModulesByUserId(ShiroUtils.getCurrentUserId(), adminConfig.getAppCode());
        modelMap.put("moduleList", ModuleDTOMapper.MAPPER.dtoToVM(moduleList));
        return "index";
    }

}