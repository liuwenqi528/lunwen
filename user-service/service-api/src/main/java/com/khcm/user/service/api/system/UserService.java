package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.LoginDTO;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.system.UserInfoParam;
import com.khcm.user.service.param.business.system.UserParam;

import java.util.List;
import java.util.Set;

/**
 * 用户服务
 * Created by yangwb on 2017/12/4.
 */
public interface UserService {

    //基本方法
    UserDTO saveOrUpdate(UserParam userParam);
    void remove(List<Integer> ids);
    UserDTO getById(Integer id);
    List<UserDTO> getList(UserParam userParam);
    PageDTO<UserDTO> getPage(UserParam userParam);

    //其它方法
    LoginDTO getByUsername(String username);
    Integer errorRemaining(String username);
    void loginSuccess(String username, String ip);
    void loginFailaure(String username, String ip);

    void setRole(Integer userId, List<Integer> roleIdList);

    Set<UserRoleDTO> getUserRoleList(Integer roleId);
    Set<UserRoleDTO> getRoleSetUserList(Integer roleId,List<Integer> ids);

    UserDTO changePwd(Integer uid,String pwd);

    UserDTO editUserInfo(UserInfoParam userInfoParam);

    LoginDTO getByEmail(String email);
    LoginDTO getByPhone(String phone);
}
