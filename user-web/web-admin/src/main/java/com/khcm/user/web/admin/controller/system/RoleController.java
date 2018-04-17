package com.khcm.user.web.admin.controller.system;

import com.khcm.user.service.api.organization.PersonService;
import com.khcm.user.service.api.system.AuthorizationService;
import com.khcm.user.service.api.system.RoleService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.param.business.system.AuthorizationParam;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.*;
import com.khcm.user.web.admin.model.parammodel.business.system.AuthorizationPM;
import com.khcm.user.web.admin.model.parammodel.business.system.RolePM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.RoleVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.UserVM;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色管理
 * Created by LiuWenqi on 2017/11/28.
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {

    public static final String BASE_PATH = "business/system/role/";

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private PersonService personService;

    @Autowired
    private AuthorizationService authorizationService;

    @RequiresPermissions("sys:role:view")
    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name="查询角色信息")
    public PageVM<RoleVM> doDatagrid(RolePM rolePM) {
        RoleParam roleParam = RoleDTOMapper.MAPPER.pmToParam(rolePM);
        PageDTO<RoleDTO> page = roleService.getPage(roleParam);
        return VMUtils.pageSuccess(page.getTotalCount(), RoleDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping("/doTable")
    @ResponseBody
    public List<RoleVM> doTable(RolePM rolePM) {
        RoleParam roleParam = RoleDTOMapper.MAPPER.pmToParam(rolePM);
        List<RoleDTO> roleDTOList = roleService.getList(roleParam);
        return RoleDTOMapper.MAPPER.dtoToVM(roleDTOList);
    }

    @RequiresPermissions("sys:role:add")
    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return BASE_PATH + "add";
    }

    @RequiresPermissions("sys:role:add")
    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name="保存角色信息")
    public ResultVM doSave(RolePM rolePM) {
        RoleParam roleParam = RoleDTOMapper.MAPPER.pmToParam(rolePM);
        roleService.saveOrUpdate(roleParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            RoleDTO roleDTO = roleService.getById(id);
            model.addAttribute("roleVM", RoleDTOMapper.MAPPER.dtoToVM(roleDTO));
        }
        return BASE_PATH + "edit";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name="修改角色信息")
    public ResultVM doUpdate(RolePM rolePM) {
        RoleParam roleParam = RoleDTOMapper.MAPPER.pmToParam(rolePM);
        roleService.saveOrUpdate(roleParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:role:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name="删除角色信息")
    public ResultVM doRemove(String ids, RedirectAttributes attr) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            roleService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:role:setAuth")
    @RequestMapping("/toSetAuthPage")
    public String toSetAuthPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            RoleUserDTO roleDTO = roleService.getRoleUserById(id);
            model.addAttribute("roleVM", RoleUserDTOMapper.MAPPER.dtoToVM(roleDTO));
        }
        return BASE_PATH + "allocPermission";
    }

    @RequiresPermissions("sys:role:setAuth")
    @RequestMapping("/doSetAuth")
    @ResponseBody
    @OperationLog(name="角色设置权限")
    public ResultVM doSetting(AuthorizationPM authorizationPM) {
        AuthorizationParam authorizationParam = AuthorizationDTOMapper.MAPPER.pmToParam(authorizationPM);
        authorizationService.saveOrUpdate(authorizationParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:role:setUser")
    @RequestMapping("/toSetUserPage")
    public String toSetUserPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            RoleUserDTO roleDTO = roleService.getRoleUserById(id);
            model.addAttribute("roleVM", RoleUserDTOMapper.MAPPER.dtoToVM(roleDTO));
            Set<UserRoleDTO> userRoleDTOs = userService.getUserRoleList(id);

            Optional<Set<UserRoleDTO>> userRoleDTOSet = Optional.ofNullable(userRoleDTOs);
            Optional<List<Integer>> userIds = userRoleDTOSet.map(
                    userRoleDTOS -> userRoleDTOS.stream()
                            .map(UserRoleDTO::getId)
                            .collect(Collectors.toList())
            );

            Set<UserRoleDTO> userNotRoleDTOs=userService.getRoleSetUserList(id,userIds.get());
            model.addAttribute("exitUsers", UserRoleDTOMapper.MAPPER.dtoToVM(userRoleDTOs));
            model.addAttribute("listUsers", UserRoleDTOMapper.MAPPER.dtoToVM(userNotRoleDTOs));
        }
        return BASE_PATH + "allocUser";
    }

    @RequiresPermissions("sys:user:setUser")
    @RequestMapping("/doSetUser")
    @ResponseBody
    @OperationLog(name="角色分配用户")
    public ResultVM doSetUser(Integer roleId, String userIds) {
        List<Integer> userIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(userIds)) {
            userIdList = Arrays.stream(userIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        }
        roleService.setUser(roleId, userIdList);

        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:role:setUser")
    @RequestMapping("/doUserDatagrid")
    @ResponseBody
    public PageVM<UserVM> doUserDatagrid(Integer roleId,Integer departmentId) {
        PageDTO<UserRoleDTO> exitUsers =personService.getExitRoleUsers(roleId,departmentId);
        return VMUtils.pageSuccess(exitUsers.getTotalCount(), UserRoleDTOMapper.MAPPER.dtoToVM(exitUsers.getContent()));
    }

    @RequiresPermissions("sys:role:setUser")
    @RequestMapping("/doNotSetUserDatagrid")
    @ResponseBody
    public PageVM<UserVM> doNotSetUserDatagrid(Integer roleId,Integer departmentId){
        PageDTO<UserRoleDTO> setRoleUsers =personService.getSetRoleUsers(roleId,departmentId);
        return VMUtils.pageSuccess(setRoleUsers.getTotalCount(), UserRoleDTOMapper.MAPPER.dtoToVM(setRoleUsers.getContent()));
    }

    /**@author Liuwenqi
     * @date 2018-02-26
     * @param rolePM
     * @return
     * changed by liuwenqi on 2018-02-01
     * 用于验证系统编码或系统名称是否重复
     */
    @RequiresPermissions({"sys:role:add","sys:role:edit"})
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(RolePM rolePM) {
        RoleParam roleParam = RoleDTOMapper.MAPPER.pmToParam(rolePM);
        RoleDTO areaDTO = roleService.getOne(roleParam);
        ResultVM resultVM = Optional.ofNullable(areaDTO).map(role->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }
}
