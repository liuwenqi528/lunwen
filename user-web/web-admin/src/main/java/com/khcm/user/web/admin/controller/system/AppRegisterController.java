package com.khcm.user.web.admin.controller.system;

import com.khcm.user.service.api.system.AppService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.param.business.system.AppParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.AppDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AppPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AppVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统注册管理
 * Created by yangwb on 2017/12/4.
 */
@Controller
@RequestMapping("/sys/appRegister")
public class AppRegisterController {

    public static final String BASE_PATH = "business/system/appRegister/";

    @Autowired
    private AppService appService;

    @RequiresPermissions("sys:appRegister:view")
    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("sys:appRegister:view")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name="查询系统信息")
    public PageVM<AppVM> doDatagrid(AppPM appPM) {
        AppParam appParam = AppDTOMapper.MAPPER.pmToParam(appPM);
        PageDTO<AppDTO> page = appService.getPage(appParam);
        return VMUtils.pageSuccess(page.getTotalCount(), AppDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }

    @RequiresPermissions("sys:appRegister:add")
    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return BASE_PATH + "add";
    }

    @RequiresPermissions("sys:appRegister:add")
    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name="保存系统信息")
    public ResultVM doSave(AppPM appPM) {
        AppParam appParam = AppDTOMapper.MAPPER.pmToParam(appPM);
        appService.saveOrUpdate(appParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:appRegister:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            AppDTO appDTO = appService.getById(id);
            model.addAttribute("appVM", AppDTOMapper.MAPPER.dtoToVM(appDTO));
        }
        return BASE_PATH + "edit";
    }
    @RequiresPermissions("sys:appRegister:info")
    @RequestMapping("/toInfoPage")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            AppDTO appDTO = appService.getById(id);
            model.addAttribute("appVM", AppDTOMapper.MAPPER.dtoToVM(appDTO));
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("sys:appRegister:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改系统信息")
    public ResultVM  doUpdate(AppPM appPM) {
        AppParam appParam = AppDTOMapper.MAPPER.pmToParam(appPM);
        appService.saveOrUpdate(appParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:appRegister:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="删除系统信息")
    public ResultVM  doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            appService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:appRegister:view")
    @RequestMapping("/doComboBox")
    @ResponseBody
    public List<AppVM> doComboBox() {
        return AppDTOMapper.MAPPER.dtoToVM(appService.getList(new AppParam()));
    }

    /**@author QimengDuan
     * @date 2018-01-30
     * @param appPM
     * @return
     * changed by liuwenqi on 2018-02-01
     * 用于验证系统编码或系统名称是否重复
     */
    @RequiresPermissions("sys:appRegister:view")
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(AppPM appPM) {
        AppParam appParam = AppDTOMapper.MAPPER.pmToParam(appPM);
        AppDTO appDTO = appService.getOne(appParam);
        ResultVM resultVM =Optional.ofNullable(appDTO).map(ad->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }
}

