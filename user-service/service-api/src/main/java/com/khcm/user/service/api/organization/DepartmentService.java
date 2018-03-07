package com.khcm.user.service.api.organization;

import com.khcm.user.service.dto.business.organization.DepartmentDTO;
import com.khcm.user.service.param.business.organization.DepartmentParam;

import java.util.List;

/**
 * 部门服务
 * Created by yangwb on 2017/12/4.
 */
public interface DepartmentService {


    List<DepartmentDTO> getList(DepartmentParam departmentParam);

    DepartmentDTO getById(Integer id);

    DepartmentDTO saveOrUpdate(DepartmentParam departmentParam);

    void remove(List<Integer> idList);
}
