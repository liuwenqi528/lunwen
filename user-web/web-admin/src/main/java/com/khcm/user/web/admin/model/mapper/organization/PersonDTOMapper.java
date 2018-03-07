package com.khcm.user.web.admin.model.mapper.organization;


import com.khcm.user.service.dto.business.organization.PersonDTO;
import com.khcm.user.service.param.business.organization.PersonParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.organization.PersonPM;
import com.khcm.user.web.admin.model.viewmodel.business.organization.PersonVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface PersonDTOMapper extends DTOMapper<PersonDTO, PersonVM, PersonPM, PersonParam> {
    PersonDTOMapper MAPPER = Mappers.getMapper(PersonDTOMapper.class);
}
