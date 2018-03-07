package com.khcm.user.web.admin.controller.system;

import com.khcm.user.service.api.system.AreaService;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.param.business.system.AreaParam;
import com.khcm.user.service.param.business.system.ResourceParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.mapper.system.AreaDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.ResourceDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AreaPM;
import com.khcm.user.web.admin.model.parammodel.business.system.ResourcePM;
import com.khcm.user.web.admin.model.viewmodel.base.ListVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AreaVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.commons.lang3.StringUtils;
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
 * Created by rqn on 2018/1/12.
 */
@Controller
@RequestMapping("/sys/area")
public class AreaController {

    public static final String BASH_PATH = "business/system/area/";

    @Autowired
    private AreaService areaService;

    @RequiresPermissions("sys:area:view")
    @GetMapping
    public String toIndexPage() {
        return BASH_PATH + "index";
    }

    @RequiresPermissions("sys:area:add")
    @GetMapping("/toAddPage")
    public String toAddPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            AreaDTO areaDTO = areaService.getById(id);
            model.addAttribute("areaVM", AreaDTOMapper.MAPPER.dtoToVM(areaDTO));
        }
        return BASH_PATH + "add";
    }

    @RequiresPermissions({"sys:area:add", "sys:area:edit"})
    @RequestMapping({"/doSave", "/doUpdate"})
    @ResponseBody
    @OperationLog(name = "编辑区域信息")
    public ResultVM doSave(AreaPM areaPM) {
        AreaParam areaParam = AreaDTOMapper.MAPPER.pmToParam(areaPM);
        areaService.saveOrUpdate(areaParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:area:view")
    @RequestMapping("/doTreegrid")
    @ResponseBody
    @OperationLog(name = "查询区域列表信息")
    public List<AreaVM> doTreegrid() {
        List<AreaDTO> areaDTOS = areaService.getList();
        return AreaDTOMapper.MAPPER.dtoToVM(areaDTOS);
    }

    @RequiresPermissions("sys:area:info")
    @RequestMapping("/toInfoPage")
    @OperationLog(name = "查看区域信息")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            AreaDTO areaDTO = areaService.getById(id);
            model.addAttribute("areaVM", AreaDTOMapper.MAPPER.dtoToVM(areaDTO));
        }
        return BASH_PATH + "info";
    }

    @RequiresPermissions("sys:area:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            AreaDTO areaDTO = areaService.getById(id);
            model.addAttribute("areaVM", AreaDTOMapper.MAPPER.dtoToVM(areaDTO));
        }
        return BASH_PATH + "edit";
    }

    @RequiresPermissions("sys:area:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name = "删除区域信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNoneBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            areaService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }


    @RequiresPermissions("sys:area:view")
    @RequestMapping("/findChildArea")
    @ResponseBody
    public ListVM<AreaVM> findChildArea(Integer pid) {
        List<AreaDTO> areaDTOList = areaService.findAllByParentId(pid);
        List<AreaVM> areaVMS = AreaDTOMapper.MAPPER.dtoToVM(areaDTOList);

        return VMUtils.listSuccess(areaVMS);
    }


    /**@author Liuwenqi
     * @date 2018-02-26
     * @param areaPM
     * @return
     * changed by liuwenqi on 2018-02-01
     * 用于验证地区是否重复
     */
    @RequiresPermissions("sys:area:view")
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(AreaPM areaPM) {
        AreaParam areaParam = AreaDTOMapper.MAPPER.pmToParam(areaPM);
        AreaDTO areaDTO = areaService.getOne(areaParam);
        ResultVM resultVM = Optional.ofNullable(areaDTO).map(area->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }



}
