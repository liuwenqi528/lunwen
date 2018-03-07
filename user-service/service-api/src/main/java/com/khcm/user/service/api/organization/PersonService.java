package com.khcm.user.service.api.organization;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.organization.PersonDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.param.business.organization.PersonParam;

import java.util.List;

/**
 * 人员服务
 * Created by yangwb on 2017/12/4.
 */
public interface PersonService {

    List<UserDTO> getList(PersonParam personParam);

    PersonDTO saveOrUpdate(PersonParam personParam);

    void remove(List<Integer> ids);

    PageDTO<PersonDTO> getPage(PersonParam personParam);

    PageDTO<UserDTO> getUserPage(PersonParam personParam);

    List<UserDTO> findAllUsableUser();

    PersonDTO getById(Integer id);


    PageDTO<UserRoleDTO> getExitRoleUsers(Integer roleId,Integer departmentId);

    PageDTO<UserRoleDTO> getSetRoleUsers(Integer roleId,Integer departmentId);

}
