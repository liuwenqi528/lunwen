package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.service.param.business.system.UserParam;

import java.util.List;

/**
 * 角色服务
 * Created by yangwb on 2017/12/4.
 */
public interface RoleService {

    //基本方法
    RoleDTO saveOrUpdate(RoleParam roleParam);

    void remove(List<Integer> ids);

    RoleDTO getById(Integer id);

    List<RoleDTO> getList(RoleParam roleParam);

    PageDTO<RoleDTO> getPage(RoleParam roleParam);

    void setUser(Integer roleId, List<Integer> userIdList);

    RoleUserDTO getRoleUserById(Integer id);

    RoleDTO getOne(RoleParam roleParam);
}
