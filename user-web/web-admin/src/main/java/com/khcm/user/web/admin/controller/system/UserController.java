package com.khcm.user.web.admin.controller.system;

import com.khcm.user.common.constant.Constants;
import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.service.api.sms.SmsService;
import com.khcm.user.service.api.system.AreaService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.sms.SmsCodeDTO;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.dto.business.system.LoginDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.system.UserInfoParam;
import com.khcm.user.service.param.business.system.UserParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.system.AreaDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.UserDTOMapper;
import com.khcm.user.web.admin.model.mapper.system.UserInfoDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ChangePwdPM;
import com.khcm.user.web.admin.model.parammodel.business.system.UserPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AreaVM;
import com.khcm.user.web.admin.model.viewmodel.business.system.UserVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author rqn
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {

    public static final String BASE_PATH = "business/system/user/";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    @RequiresPermissions("sys:user:view")
    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name = "查询用户列表信息")
    public PageVM<UserVM> doDatagrid(UserPM userPM) {
        UserParam userParam = UserDTOMapper.MAPPER.pmToParam(userPM);
        PageDTO<UserDTO> page = userService.getPage(userParam);
        return VMUtils.pageSuccess(page.getTotalCount(), UserDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }

    @RequiresPermissions("sys:user:add")
    @RequestMapping("/toAddPage")
    public String toAddPage(Model model) {
        return BASE_PATH + "add";
    }

    @RequiresPermissions({"sys:user:add", "sys:user:edit"})
    @RequestMapping({"/doSave", "/doUpdate"})
    @ResponseBody
    @OperationLog(name = "编辑用户信息")
    public ResultVM doSave(UserPM userPM) {
        UserParam userParam = UserDTOMapper.MAPPER.pmToParam(userPM);
        userService.saveOrUpdate(userParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:user:info")
    @RequestMapping("/toInfoPage")
    @OperationLog(name = "查看用户信息")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            UserDTO userDTO = userService.getById(id);
            UserVM userVM = UserDTOMapper.MAPPER.dtoToVM(userDTO);
            model.addAttribute("userVM", userVM);
        }
        return BASE_PATH + "info";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            UserDTO userDTO = userService.getById(id);
            UserVM userVM = UserDTOMapper.MAPPER.dtoToVM(userDTO);
            model.addAttribute("userVM", userVM);
        }
        return BASE_PATH + "edit";
    }

    @RequiresPermissions("sys:user:setRole")
    @RequestMapping("/toSetRolePage")
    public String toSetRolePage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            UserDTO userDTO = userService.getById(id);
            model.addAttribute("userVM", UserDTOMapper.MAPPER.dtoToVM(userDTO));
        }
        return BASE_PATH + "allocRole";
    }


    @RequiresPermissions("sys:user:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name = "删除用户")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            userService.remove(idList);
        }

        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:user:setRole")
    @RequestMapping("/doSetRole")
    @ResponseBody
    @OperationLog(name = "设置用户角色信息")
    public ResultVM doSetRole(Integer userId, String roleIds) {
        if (StringUtils.isNotBlank(roleIds)) {
            List<Integer> roleIdList = Arrays.stream(roleIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());

            userService.setRole(userId, roleIdList);
        }

        return VMUtils.resultSuccess();
    }

    /**
     * 验证用户名、邮箱、电话唯一性
     * @param value
     * @param type
     * @return
     */
    @RequiresPermissions("sys:user:setRole")
    @RequestMapping("/doVerify")
    @ResponseBody
    public Integer doVerify(String value, String type) {
        Integer flag = 0;
        if (StringUtils.isNotBlank(value)) {
            LoginDTO loginDTO = null;
            if (USERNAME.equals(type)) {
                loginDTO = userService.getByUsername(value);
            } else if (EMAIL.equals(type)) {
                loginDTO = userService.getByEmail(value);
            } else if (PHONE.equals(type)) {
                loginDTO = userService.getByPhone(value);
            }
            if (loginDTO != null) {
                flag = loginDTO.getId();
            } else {
                flag = 0;
            }
        }
        return flag;
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping("/toInfoDetailPage")
    public String toInfoDetailPage(Integer uid, Model model) {
        UserDTO userDTO = userService.getUserAndArea(uid);
        UserVM userVM = UserDTOMapper.MAPPER.dtoToVM(userDTO);
        //获取初级地域列表
        List<AreaDTO> areaDTOAllList = areaService.findAllByParentId(null);
        if (Objects.nonNull(userDTO.getAreaDTOList())) {
            List<AreaDTO> exitAreas = userDTO.getAreaDTOList();
            List<AreaDTO> temp = new ArrayList<>();
            areaDTOAllList.stream().forEach(areaDTO -> {
                if (!exitAreas.contains(areaDTO)) {
                    temp.add(areaDTO);
                }
            });
            areaDTOAllList.removeAll(temp);
            List<AreaVM> areaVMS = AreaDTOMapper.MAPPER.dtoToVM(exitAreas);
            Collections.reverse(areaVMS);
            userVM.setAreas(areaVMS);
        }
        model.addAttribute("areas", AreaDTOMapper.MAPPER.dtoToVM(areaDTOAllList));
        model.addAttribute("user", userVM);
        return BASE_PATH + "infoDetail";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping("/editUserInfo")
    @ResponseBody
    @OperationLog(name = "编辑个人资料信息")
    public ResultVM editUserInfo(UserPM userPM) {
        UserInfoParam userInfoParam = UserInfoDTOMapper.MAPPER.pmToParam(userPM);
        userService.editUserInfo(userInfoParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping("/toChangePwdPage")
    public String toChangePwdPage(Integer uid, Model model) {
        UserDTO userDTO = userService.getById(uid);
        model.addAttribute("uid", userDTO.getId());
        model.addAttribute("username", userDTO.getUsername());
        return BASE_PATH + "changePwd";
    }

    /**
     * @param changePwdPM
     * @return
     * @author QimengDuan
     * @date 2018-01-26
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping("/doValidatePwd")
    @ResponseBody
    public ResultVM doValidatePwd(ChangePwdPM changePwdPM) {
        //1.验证密码是否正确；
        UserDTO userDTO = userService.getById(changePwdPM.getUserId());
        String cryPassword = new SimpleHash(Constants.HASH_ALGORITHM_NAME, changePwdPM.getPassword(), userDTO.getUsername(), Constants.HASH_ITERATIONS).toString();
        if (userDTO.getPassword().equals(cryPassword)) {
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }

    /**
     * @param changePwdPM
     * @return
     * @author QimengDuan
     * @date 2018-01-26
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping("/doUpdateWord")
    @ResponseBody
    @OperationLog(name = "修改用户密码")
    public ResultVM doUpdateWord(ChangePwdPM changePwdPM) {
        String newpwd = changePwdPM.getNewPwd();
        UserDTO userDTO = userService.getById(changePwdPM.getUserId());
        if (StringUtils.isNotBlank(newpwd)) {
            String newPwd = new SimpleHash(Constants.HASH_ALGORITHM_NAME, changePwdPM.getNewPwd(), userDTO.getUsername(), Constants.HASH_ITERATIONS).toString();
            userService.changePwd(changePwdPM.getUserId(), newPwd);
            return VMUtils.resultSuccess();
        }
        return VMUtils.resultFailure();
    }

}