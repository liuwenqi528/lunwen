package com.khcm.user.service.api.organization;

import com.khcm.user.service.dto.business.organization.OrganizationDTO;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.param.business.organization.OrganizationParam;
import com.khcm.user.service.param.business.system.ResourceParam;

import java.util.List;

/**
 * 机构服务
 * Created by yangwb on 2017/12/4.
 */
public interface OrganizationService {
    List<OrganizationDTO> getList(OrganizationParam organizationParam);

    OrganizationDTO saveOrUpdate(OrganizationParam organizationParam);

    void remove(List<Integer> ids);

    OrganizationDTO getById(Integer id);

}

