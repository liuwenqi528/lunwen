package com.khcm.user.web.admin.controller.org;

import com.khcm.user.service.api.organization.OrganizationService;
import com.khcm.user.service.dto.business.organization.OrganizationDTO;
import com.khcm.user.service.param.business.organization.OrganizationParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.config.AdminConfig;
import com.khcm.user.web.admin.model.mapper.organization.OrganizationDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.OrganizationPM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.OrganizationVM;
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
import java.util.stream.Collectors;

/**
 * 机构管理
 * Created by liuwenqi on 2017/12/4.
 */
@Controller
@RequestMapping("/org/organization")
public class OrganizationController {

    public static final String BASE_PATH = "business/org/organization/";

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AdminConfig adminConfig;

    @GetMapping
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("/doTreegrid")
    @ResponseBody
    @OperationLog(name="查询组织机构信息")
    public List<OrganizationVM> doTreegrid(OrganizationPM organizationPM) {
        OrganizationParam organizationParam = OrganizationDTOMapper.MAPPER.pmToParam(organizationPM);
        List<OrganizationDTO> organizationDTOList = organizationService.getList(organizationParam);
        return OrganizationDTOMapper.MAPPER.dtoToVM(organizationDTOList);
    }

    @RequestMapping("/toAddPage")
    public String toAddPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            OrganizationDTO organizationDTO = organizationService.getById(id);
            model.addAttribute("organizationVM", OrganizationDTOMapper.MAPPER.dtoToVM(organizationDTO));
        }
        return BASE_PATH + "add";
    }

    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name="保存组织机构信息")
    public ResultVM doSave(OrganizationPM organizationPM) {
        OrganizationParam organizationParam = OrganizationDTOMapper.MAPPER.pmToParam(organizationPM);
        OrganizationDTO organizationDTO = organizationService.saveOrUpdate(organizationParam);
        if (Objects.nonNull(organizationDTO)) {
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }

    @RequiresPermissions("org:organization:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            OrganizationDTO organizationDTO = organizationService.getById(id);
            model.addAttribute("organizationVM", OrganizationDTOMapper.MAPPER.dtoToVM(organizationDTO));
        }
        return BASE_PATH + "edit";
    }


    @RequiresPermissions("org:organization:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改组织机构信息")
    public ResultVM doUpdate(OrganizationPM organizationPM, RedirectAttributes attr) {
        OrganizationParam organizationParam = OrganizationDTOMapper.MAPPER.pmToParam(organizationPM);
        OrganizationDTO organizationDTO = organizationService.saveOrUpdate(organizationParam);
        if (Objects.nonNull(organizationDTO)) {
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }


    @RequiresPermissions("org:organization:info")
    @RequestMapping("/toInfoPage")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            OrganizationDTO organizationDTO = organizationService.getById(id);
            model.addAttribute("organizationVM", OrganizationDTOMapper.MAPPER.dtoToVM(organizationDTO));
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("org:organization:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="删除组织机构信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            organizationService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }
}
