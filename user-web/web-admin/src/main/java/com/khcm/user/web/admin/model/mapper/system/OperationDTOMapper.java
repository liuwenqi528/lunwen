package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.OperationDTO;
import com.khcm.user.service.param.business.system.OperationParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.OperationPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.OperationVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface OperationDTOMapper extends DTOMapper<OperationDTO, OperationVM, OperationPM, OperationParam> {
    OperationDTOMapper MAPPER = Mappers.getMapper(OperationDTOMapper.class);
}
