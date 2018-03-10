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
    @Autowired
    private AppService appService;

    @RequiresPermissions("sys:appResource:view")
    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("sys:appResource:view")
    @RequestMapping("doTreegrid")
    @ResponseBody
    @OperationLog(name="查询系统资源信息")
    public List<ResourceVM> doTreegrid(ResourcePM resourcePM) {
        ResourceParam resourceParam = ResourceDTOMapper.MAPPER.pmToParam(resourcePM);
        List<ResourceDTO> resources = resourceService.getList(resourceParam);
        return ResourceDTOMapper.MAPPER.dtoToVM(resources);
    }

    @RequiresPermissions("sys:appResource:add")
    @RequestMapping("/toAddPage")
    public String toAddPage(Integer id, String appCode, Model model) {
        if (Objects.nonNull(id)) {
            ResourceDTO resourceDTO = resourceService.getById(id);
            model.addAttribute("resourceVM", ResourceDTOMapper.MAPPER.dtoToVM(resourceDTO));
        } else {
            AppDTO appDTO = appService.getByCode(appCode);
            model.addAttribute("appVM", AppDTOMapper.MAPPER.dtoToVM(appDTO));
        }
        return BASE_PATH + "add";
    }

    @RequiresPermissions("sys:appResource:add")
    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name="保存系统资源信息")
    public ResultVM doSave(ResourcePM resourcePM) {
        ResourceParam resourceParam = ResourceDTOMapper.MAPPER.pmToParam(resourcePM);
        resourceService.saveOrUpdate(resourceParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:appResource:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            ResourceDTO resourceDTO = resourceService.getById(id);
            model.addAttribute("resourceVM", ResourceDTOMapper.MAPPER.dtoToVM(resourceDTO));
        }
        return BASE_PATH + "edit";
    }

    @RequiresPermissions("sys:appResource:info")
    @RequestMapping("/toInfoPage")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            ResourceDTO resourceDTO = resourceService.getById(id);
            model.addAttribute("resourceVM", ResourceDTOMapper.MAPPER.dtoToVM(resourceDTO));
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("sys:appResource:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改系统资源信息")
    public ResultVM doUpdate(ResourcePM resourcePM) {
        ResourceParam resourceParam = ResourceDTOMapper.MAPPER.pmToParam(resourcePM);
        resourceService.saveOrUpdate(resourceParam);
        return VMUtils.resultSuccess();
    }


    @RequiresPermissions("sys:appResource:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="删除系统资源信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            resourceService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }

    /**@author Liuwenqi
     * @date 2018-02-26
     * @param resourcePM
     * @return
     * changed by liuwenqi on 2018-02-01
     * 用于验证系统编码或系统名称是否重复
     */
    @RequiresPermissions("sys:appResource:view")
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(ResourcePM resourcePM) {
        ResourceParam resourceParam = ResourceDTOMapper.MAPPER.pmToParam(resourcePM);
        ResourceDTO resourceDTO = resourceService.getOne(resourceParam);
        ResultVM resultVM = Optional.ofNullable(resourceDTO).map(ar->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }


}

