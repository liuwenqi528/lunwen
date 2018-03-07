package com.khcm.user.web.admin.model.mapper.organization;

import com.khcm.user.service.dto.business.organization.OrganizationDTO;
import com.khcm.user.service.param.business.organization.OrganizationParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.OrganizationPM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.OrganizationVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface OrganizationDTOMapper extends DTOMapper<OrganizationDTO, OrganizationVM, OrganizationPM, OrganizationParam> {
    OrganizationDTOMapper MAPPER = Mappers.getMapper(OrganizationDTOMapper.class);
}
