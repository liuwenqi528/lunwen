package com.khcm.user.web.admin.controller.org;

import com.khcm.user.service.api.organization.DepartmentService;
import com.khcm.user.service.dto.business.organization.DepartmentDTO;
import com.khcm.user.service.param.business.organization.DepartmentParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.organization.DepartmentDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.DepartmentPM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.DepartmentVM;
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
import java.util.stream.Collectors;


/**
 * 部门管理
 * Created by liuwenqi on 2017/12/4.
 */
@Controller
@RequestMapping("/org/department")
public class DepartmentController {

    public static final String BASE_PATH = "business/org/department/";

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String index() {
        return BASE_PATH + "index";
    }

    /**
     * 根据条件进行查询
     *
     * @param departmentPM
     * @return
     */
    @RequestMapping("/doTreegrid")
    @ResponseBody
    @OperationLog(name="部门查询")
    public List<DepartmentVM> doTreegrid(DepartmentPM departmentPM) {
        DepartmentParam departmentParam = DepartmentDTOMapper.MAPPER.pmToParam(departmentPM);
        List<DepartmentDTO> departmentDTOS = departmentService.getList(departmentParam);
        return DepartmentDTOMapper.MAPPER.dtoToVM(departmentDTOS);
    }

    @RequestMapping("/toAddPage")
    public String toAddPage(Integer id,Integer organizationId, Model model) {
        if (Objects.nonNull(id)) {
            DepartmentDTO departmentDTO = departmentService.getById(id);
            model.addAttribute("departmentVM", DepartmentDTOMapper.MAPPER.dtoToVM(departmentDTO));
        }else if(Objects.nonNull(organizationId)){
            DepartmentDTO departmentDTO=new DepartmentDTO();
            departmentDTO.setOrganizationId(organizationId);
            model.addAttribute("departmentVM", DepartmentDTOMapper.MAPPER.dtoToVM(departmentDTO));
        }
        return BASE_PATH + "add";
    }

    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name="保存部门信息")
    public ResultVM doSave(DepartmentPM departmentPM) {
        DepartmentParam departmentParam = DepartmentDTOMapper.MAPPER.pmToParam(departmentPM);
        DepartmentDTO departmentDTO = departmentService.saveOrUpdate(departmentParam);
        if (Objects.nonNull(departmentDTO)) {
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }

    @RequiresPermissions("org:department:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            DepartmentDTO departmentDTO = departmentService.getById(id);
            model.addAttribute("departmentVM", DepartmentDTOMapper.MAPPER.dtoToVM(departmentDTO));
        }
        return BASE_PATH + "edit";
    }


    @RequiresPermissions("org:department:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改部门信息")
    public ResultVM doUpdate(DepartmentPM departmentPM) {
        DepartmentParam departmentParam = DepartmentDTOMapper.MAPPER.pmToParam(departmentPM);
        DepartmentDTO departmentDTO = departmentService.saveOrUpdate(departmentParam);
        if (Objects.nonNull(departmentDTO)) {
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }


    @RequiresPermissions("org:department:info")
    @RequestMapping("/toInfoPage")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            DepartmentDTO departmentDTO = departmentService.getById(id);
            model.addAttribute("departmentVM", DepartmentDTOMapper.MAPPER.dtoToVM(departmentDTO));
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("org:department:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="删除部门信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            departmentService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }
}