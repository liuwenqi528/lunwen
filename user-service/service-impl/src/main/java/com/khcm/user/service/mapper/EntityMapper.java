package com.khcm.user.service.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

public interface EntityMapper<ENTITY, DTO, PARAM> {

    DTO entityToDTO(ENTITY entity);

    List<DTO> entityToDTO(List<ENTITY> entityList);

    Set<DTO> entityToDTO(Set<ENTITY> entitySet);

    ENTITY paramToEntity(PARAM param);

    List<ENTITY> paramToEntity(List<PARAM> paramList);

    Set<ENTITY> paramToSetEntity(List<PARAM> paramSet);

    DTO entityToDTO(ENTITY entity, @MappingTarget DTO dto);

    List<DTO> entityToDTO(List<ENTITY> entityList, @MappingTarget List<DTO> dtoList);

    Set<DTO> entityToDTO(Set<ENTITY> entitySet, @MappingTarget Set<DTO> dtoSet);

    ENTITY paramToEntity(PARAM param, @MappingTarget ENTITY entity);

    List<ENTITY> paramToEntity(List<PARAM> paramList, @MappingTarget List<ENTITY> entityList);

}
