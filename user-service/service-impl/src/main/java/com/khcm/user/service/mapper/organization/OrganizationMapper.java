package com.khcm.user.service.mapper.organization;


import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.service.dto.business.organization.OrganizationDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.organization.OrganizationParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper extends EntityMapper<Organization, OrganizationDTO, OrganizationParam> {

    OrganizationMapper MAPPER = Mappers.getMapper(OrganizationMapper.class);
    @Override
    @Mapping(target = "text", source = "name")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    OrganizationDTO entityToDTO(Organization entity);

    @Override
    @Mapping(target = "parent", expression = "java(getParent(organizationParam.getParentId()))")
    Organization paramToEntity(OrganizationParam organizationParam);

    default Organization getParent(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return null;
        }

        Organization organization = new Organization();
        organization.setId(parentId);
        return organization;
    }


}
