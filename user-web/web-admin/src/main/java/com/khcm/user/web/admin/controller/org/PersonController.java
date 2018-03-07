package com.khcm.user.web.admin.controller.org;

import com.khcm.user.service.api.organization.PersonService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.organization.PersonDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.organization.PersonParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.organization.PersonDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.UserDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.PersonPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.PersonVM;
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
 * 人员管理
 * Created by yangwb on 2017/12/4.
 */
@Controller
@RequestMapping("/org/person")
public class PersonController {

    public static final String BASE_PATH = "business/org/person/";

    @Autowired
    private PersonService personService;

    @GetMapping
    public String index() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("org:person:add")
    @RequestMapping("/toAddPage")
    public String toAddPage(Model model) {
        List<UserDTO> userDTOList = personService.findAllUsableUser();
        model.addAttribute("users", UserDTOMapper.MAPPER.dtoToVM(userDTOList));
        return BASE_PATH + "add";
    }

    @RequiresPermissions("org:person:add")
    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name = "编辑人员信息")
    public ResultVM doSave(PersonPM personPM) {
        PersonParam personParam = PersonDTOMapper.MAPPER.pmToParam(personPM);
        personService.saveOrUpdate(personParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("org:person:view")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name = "查询人员列表信息")
    public PageVM<PersonVM> doDatagrid(PersonPM personPM) {
        PersonParam personParam=PersonDTOMapper.MAPPER.pmToParam(personPM);
        PageDTO<PersonDTO> pageDTO = personService.getPage(personParam);
        return VMUtils.pageSuccess(pageDTO.getTotalCount(), PersonDTOMapper.MAPPER.dtoToVM(pageDTO.getContent()));
    }

    @RequiresPermissions("org:person:info")
    @RequestMapping("/toInfoPage")
    @OperationLog(name = "查看人员信息")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            PersonDTO personDTO = personService.getById(id);
            model.addAttribute("personVM", PersonDTOMapper.MAPPER.dtoToVM(personDTO));
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("org:person:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            PersonDTO personDTO = personService.getById(id);
            model.addAttribute("personVM", PersonDTOMapper.MAPPER.dtoToVM(personDTO));

            List<UserDTO> userDTOList = personService.findAllUsableUser();
            model.addAttribute("users", UserDTOMapper.MAPPER.dtoToVM(userDTOList));
        }
        return BASE_PATH + "edit";
    }

    @RequiresPermissions("org:person:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name = "删除人员信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            personService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }
}
