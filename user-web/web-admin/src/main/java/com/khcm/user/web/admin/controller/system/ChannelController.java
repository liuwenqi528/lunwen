package com.khcm.user.web.admin.controller.system;

import com.khcm.user.service.api.system.ChannelService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.dto.business.system.ChannelDTO;
import com.khcm.user.service.param.business.system.AreaParam;
import com.khcm.user.service.param.business.system.ChannelParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.AreaDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.ChannelDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AreaPM;
import com.khcm.user.web.admin.model.parammodel.business.system.ChannelPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ChannelVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Created by rqn
 */
@Controller
@RequestMapping("/sys/channel")
public class ChannelController {

    public static final String BASH_PATH = "business/system/channel/";

    @Autowired
    private ChannelService channelService;

    @RequiresPermissions("sys:channel:view")
    @RequestMapping()
    public String toIndexPage() {
        return BASH_PATH + "index";
    }

    @RequiresPermissions("sys:channel:add")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name = "查询渠道列表信息")
    public PageVM<ChannelVM> doDatagrid(ChannelPM channelPM) {
        //增加了查询时的参数，使其适用于条件查询
        ChannelParam channelParam = ChannelDTOMapper.MAPPER.pmToParam(channelPM);
        PageDTO<ChannelDTO> page = channelService.getPage(channelParam);
        return VMUtils.pageSuccess(page.getTotalCount(), ChannelDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }

    @RequiresPermissions("sys:channel:add")
    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return BASH_PATH + "add";
    }

    @RequiresPermissions({"sys:channel:add", "sys:channel:edit"})
    @RequestMapping("/doSaveOrUpdate")
    @ResponseBody
    @OperationLog(name = "编辑注册渠道信息")
    public ResultVM doSaveOrUpdate(ChannelPM channelPM) {
        ChannelParam channelParam = ChannelDTOMapper.MAPPER.pmToParam(channelPM);
        channelService.saveOrUpdate(channelParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:channel:info")
    @RequestMapping("/toInfoPage")
    @OperationLog(name = "查看注册渠道信息")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id) && id > 0) {
            model.addAttribute("channelVM", ChannelDTOMapper.MAPPER.dtoToVM(channelService.getById(id)));
        }
        return BASH_PATH + "info";
    }

    @RequiresPermissions("sys:channel:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id) && id > 0) {
            model.addAttribute("channelVM", ChannelDTOMapper.MAPPER.dtoToVM(channelService.getById(id)));
        }
        return BASH_PATH + "edit";
    }

    @RequiresPermissions("sys:channel:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name = "删除注册渠道信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            channelService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }
    /**@author Liuwenqi
     * @date 2018-02-26
     * @param channelPM
     * @return
     * changed by liuwenqi on 2018-02-01
     * 用于验证注册渠道是否重复
     */
    @RequiresPermissions("sys:channel:view")
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(ChannelPM channelPM) {
        ChannelParam channelParam = ChannelDTOMapper.MAPPER.pmToParam(channelPM);
        ChannelDTO channelDTO = channelService.getOne(channelParam);
        ResultVM resultVM = Optional.ofNullable(channelDTO).map(channel->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }
}
