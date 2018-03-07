package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.common.enums.AreaType;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.param.business.system.AreaParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AreaPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AreaVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * Created by rqn on 2018/1/12.
 */
@Mapper
public interface AreaDTOMapper extends DTOMapper<AreaDTO, AreaVM, AreaPM, AreaParam> {
    AreaDTOMapper MAPPER = Mappers.getMapper(AreaDTOMapper.class);

    @Override
    @Mapping(target = "areaTypeName", expression = "java(getAreaTypeName(areaDTO.getAreaType()))")
    AreaVM dtoToVM(AreaDTO areaDTO);

    default String getAreaTypeName(AreaType areaType) {
        String typeName;
        if (Objects.isNull(areaType)) {
            return null;
        } else {
            switch (areaType) {
                case NATIONALITY:
                    typeName = "国家";
                    break;
                case PROVINCE:
                    typeName = "省/直辖市";
                    break;
                case CITY:
                    typeName = "市";
                    break;
                case COUNTY:
                    typeName = "区/县";
                    break;
                default:
                    typeName = "未标注";
            }
            return typeName;
        }

    }
}
