package com.khcm.user.web.admin.model.mapper.organization;

import com.khcm.user.common.utils.DateUtils;
import com.khcm.user.service.dto.business.organization.DepartmentDTO;
import com.khcm.user.service.param.business.organization.DepartmentParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.DepartmentPM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.DepartmentVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.Objects;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface DepartmentDTOMapper extends DTOMapper<DepartmentDTO, DepartmentVM, DepartmentPM, DepartmentParam> {

    DepartmentDTOMapper MAPPER = Mappers.getMapper(DepartmentDTOMapper.class);

    /**@author QimengDuan
     * @param departmentPM
     * @return
     */
    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(departmentPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(departmentPM.getEndTime()))")
    DepartmentParam pmToParam(DepartmentPM departmentPM);

}
