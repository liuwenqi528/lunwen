package com.khcm.user.web.admin.controller.system;

import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.api.system.AppService;
import com.khcm.user.service.api.system.ResourceService;
import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.param.business.system.AppParam;
import com.khcm.user.service.param.business.system.ResourceParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.AppDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.ResourceDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AppPM;
import com.khcm.user.web.admin.model.parammodel.business.system.ResourcePM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ResourceVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 系统资源管理
 * Created by yangwb on 2017/12/4.
 */
@Controller
@RequestMapping("/sys/appResource")
public class AppResourceController {

    public static final String BASE_PATH = "business/system/appResource/";

    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("sys:appResource:view")
    @RequestMapping("doTreegrid")
    @ResponseBody
    @OperationLog(name="查询系统资源信息")
    public List<ResourceVM> doTreegrid(ResourcePM resourcePM) {
        ResourceParam resourceParam = ResourceDTOMapper.MAPPER.pmToParam(resourcePM);
        List<ResourceDTO> resources = resourceService.getList(resourceParam);
        return ResourceDTOMapper.MAPPER.dtoToVM(resources);
    }




}

