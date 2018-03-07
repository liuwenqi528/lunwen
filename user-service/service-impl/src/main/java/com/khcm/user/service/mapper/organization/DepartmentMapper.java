package com.khcm.user.service.mapper.organization;

import com.khcm.user.dao.entity.business.orgnization.Department;
import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.dto.business.organization.DepartmentDTO;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.organization.DepartmentParam;
import com.khcm.user.service.param.business.system.ResourceParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper
public interface DepartmentMapper extends EntityMapper<Department, DepartmentDTO, DepartmentParam> {

    DepartmentMapper MAPPER = Mappers.getMapper(DepartmentMapper.class);

    @Override
    @Mapping(target = "text", source = "name")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    @Mapping(target = "organizationName", source = "organization.name")
    DepartmentDTO entityToDTO(Department entity);

    @Override
    @Mapping(target = "organization", expression = "java(getOrganization(departmentParam.getOrganizationId()))")
    Department paramToEntity(DepartmentParam departmentParam, @MappingTarget Department department);


    @Override
    @Mapping(target = "parent", expression = "java(getParent(departmentParam.getParentId()))")
    @Mapping(target = "organization", expression = "java(getOrganization(departmentParam.getOrganizationId()))")
    Department paramToEntity(DepartmentParam departmentParam);

    default Department getParent(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return null;
        }

        Department department = new Department();
        department.setId(parentId);
        return department;
    }

    default Organization getOrganization(Integer organizationId) {
        if (organizationId == null || organizationId <= 0) {
            return null;
        }

        Organization organization = new Organization();
        organization.setId(organizationId);
        return organization;
    }
}
