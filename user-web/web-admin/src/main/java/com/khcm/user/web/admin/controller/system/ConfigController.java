package com.khcm.user.web.admin.controller.system;

import com.khcm.user.service.api.system.ConfigService;
import com.khcm.user.service.dto.business.system.ConfigDTO;
import com.khcm.user.service.dto.business.system.ConfigTabDTO;
import com.khcm.user.service.param.business.system.ConfigParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.ConfigDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.ConfigTabDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ConfigPM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ConfigTabVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ConfigVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置管理
 * Created by liuwenqi on 2017/12/4.
 */
@Controller
@RequestMapping("/sys/config")
public class ConfigController {

    public static final String BASE_PATH = "business/system/config/";
    @Autowired
    private ConfigService configService;

    /**
     * 跳转到配置管理页面 ，并将所有的配置信息存入model，供页面显示
     * @param model
     * @return
     */
    @RequiresPermissions("sys:config:view")
    @GetMapping
    public String toIndexPage(Model model) {
        ConfigDTO configDTOS = configService.getListByType("BASE");
        ConfigVM configVMS = ConfigDTOMapper.MAPPER.dtoToVM(configDTOS);
        model.addAttribute("configVMList", configVMS);
        return BASE_PATH + "index";
    }

    @RequiresPermissions("sys:config:view")
    @RequestMapping("/getList")
    @ResponseBody
    public List<ConfigTabVM> getList(){
        List<ConfigTabDTO> configTabDTOS = configService.getList();
        return ConfigTabDTOMapper.MAPPER.dtoToVM(configTabDTOS);
    }


    /**
     * 添加或修改配置信息
     * @param configPM
     * @return
     */
    @RequiresPermissions("sys:config:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改配置信息")
    public ResultVM  doUpdate(@RequestBody  ConfigPM configPM) {

        ConfigParam configParams = ConfigDTOMapper.MAPPER.pmToParam(configPM);

        configService.saveOrUpdate(configParams);

        return VMUtils.resultSuccess();
    }

    /**
     *
     * @return
     */
    @RequiresPermissions("sys:config:view")
    @RequestMapping(value = "/doSelect/{type}")
    @ResponseBody
    @OperationLog(name="查询配置信息")
    public ConfigVM doSelect(@PathVariable("type") String type){
        ConfigDTO configDTOS = configService.getListByType(type);
        ConfigVM configVMS = ConfigDTOMapper.MAPPER.dtoToVM(configDTOS);
        return configVMS;
    }
}