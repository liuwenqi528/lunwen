package com.khcm.user.web.admin.controller.log;

import com.khcm.user.service.api.log.LogService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.log.LogDTO;
import com.khcm.user.service.param.business.log.LogParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.log.LogDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.log.LogPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.log.LogVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangwb on 2018/1/4.
 */
@Controller
@RequestMapping("/log/loginSuccess")
public class LoginSucessController {
    public static final String BASE_PATH = "business/log/";

    @Autowired
    private LogService logService;
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "/doDatagrid")
    @ResponseBody
    @OperationLog(name="成功日志查询")
    public PageVM<LogVM> doDatagrid(LogPM logPM) {
        LogParam logParam = LogDTOMapper.MAPPER.pmToParam(logPM);
        PageDTO<LogDTO> page = logService.getPage(logParam);
        return VMUtils.pageSuccess(page.getTotalCount(), LogDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }
    @RequiresPermissions("sys:appRegister:view")
    @GetMapping
    public String showSuccessLog() {
        return BASE_PATH+"loginSuccess";
    }

    @RequiresPermissions("sys:user:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="成功日志删除")
    public ResultVM removeById(String ids){
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            logService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }
}
