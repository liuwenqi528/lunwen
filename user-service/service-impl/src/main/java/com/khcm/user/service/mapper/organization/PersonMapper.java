package com.khcm.user.service.mapper.organization;

import com.khcm.user.dao.entity.business.orgnization.Department;
import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.orgnization.Person;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.organization.PersonDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.organization.PersonParam;
import com.khcm.user.service.param.business.system.UserParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper
public interface PersonMapper extends EntityMapper<Person, PersonDTO, PersonParam> {

    PersonMapper MAPPER = Mappers.getMapper(PersonMapper.class);


    @Override
    @Mapping(target = "department", expression = "java(getDepartment(personParam.getDepartmentId()))")
    @Mapping(target = "organization", expression = "java(getOrganization(personParam.getOrganizationId()))")
    @Mapping(target = "user", expression = "java(getUser(personParam.getUserId()))")
    Person paramToEntity(PersonParam personParam, @MappingTarget Person person);


    @Override
    @Mapping(target = "department", expression = "java(getDepartment(personParam.getDepartmentId()))")
    @Mapping(target = "organization", expression = "java(getOrganization(personParam.getOrganizationId()))")
    @Mapping(target = "user", expression = "java(getUser(personParam.getUserId()))")
    Person paramToEntity(PersonParam personParam);

    @Override
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "userId", source = "user.id")
    PersonDTO entityToDTO(Person person);

    default User getUser(Integer userId) {
        if (Objects.isNull(userId)) {
            return null;
        } else {
            User user = new User();
            user.setId(userId);
            return user;
        }
    }

    default Department getDepartment(Integer departmentId) {
        if (Objects.isNull(departmentId)) {
            return null;
        } else {
            Department department = new Department();
            department.setId(departmentId);
            return department;
        }
    }

    default Organization getOrganization(Integer organizationId) {
        if (Objects.isNull(organizationId)) {
            return null;
        } else {
            Organization organization = new Organization();
            organization.setId(organizationId);
            return organization;
        }
    }

}
