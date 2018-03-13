package com.khcm.user.service.impl.orgnization;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.dao.entity.business.orgnization.Department;
import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.orgnization.Person;
import com.khcm.user.dao.entity.business.orgnization.QPerson;
import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.master.organization.DepartmentRepository;
import com.khcm.user.dao.repository.master.organization.OrganizationRepository;
import com.khcm.user.dao.repository.master.organization.PersonRepository;
import com.khcm.user.dao.repository.master.system.RoleRepository;
import com.khcm.user.dao.repository.master.system.UserRepository;
import com.khcm.user.service.api.organization.PersonService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.organization.PersonDTO;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.mapper.organization.PersonMapper;
import com.khcm.user.service.mapper.system.UserMapper;
import com.khcm.user.service.mapper.system.UserRoleMapper;
import com.khcm.user.service.param.business.organization.PersonParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 员工业务处理 Service
 *
 * @author wangtao
 * on 2017/9/29
 */
@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<UserDTO> getList(PersonParam personParam) {
        QPerson qPerson = QPerson.person;
        BooleanExpression predicate = qPerson.enable.eq(true);
        Optional<Integer> departmentIdOptional = Optional.ofNullable(personParam.getDepartmentId());
        predicate = predicate.and(
                departmentIdOptional.map(departmentId -> {
                    Department department = departmentRepository.findOne(personParam.getDepartmentId());
                    return qPerson.department.id.in(departmentRepository.getIdsById(department.getLft(), department.getRgt()));
                }).orElse(null)
        );

        List<User> userList = new ArrayList<>();
        personRepository.findList(predicate).forEach(person -> {
            userList.add(person.getUser());
        });

        return UserMapper.MAPPER.entityToDTO(userList);
    }

    @Override
    public PersonDTO saveOrUpdate(PersonParam personParam) {
        Optional<Integer> personParamId = Optional.ofNullable(personParam.getId());
        return personParamId.map(id -> {
            Person person = personRepository.getOne(personParam.getId());
            return PersonMapper.MAPPER.entityToDTO(personRepository.save(PersonMapper.MAPPER.paramToEntity(personParam, person)));
        }).orElseGet(() -> {
            Person person = PersonMapper.MAPPER.paramToEntity(personParam);
            person.setEnable(true);
            return PersonMapper.MAPPER.entityToDTO(personRepository.save(person));
        });
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.stream().forEach(id -> personRepository.updateEnable(id, false));
    }

    /**
     * @param personParam
     * @return
     * @update by Qimeng Duan
     * @date 2018-01-25
     */
    @Override
    public PageDTO<PersonDTO> getPage(PersonParam personParam) {
        Integer organizationId = personParam.getOrganizationId();
        QPerson qPerson = QPerson.person;
        BooleanExpression predicate = qPerson.enable.eq(true);
        //如果所属机构ID不为空，则查询所属机构
        Optional<Integer> organizationIdOptional = Optional.ofNullable(organizationId);
        predicate = predicate.and(organizationIdOptional.map(orgId -> {
            Organization organization = organizationRepository.getOne(orgId);
            return qPerson.organization.id.in(organizationRepository.getIdsById(organization.getLft(), organization.getRgt()));
        }).orElse(null));


        //使用StringUtils.isNotBlank替代Objects.nonNull方法
        if (StringUtils.isNotBlank(personParam.getName())) {
            //此处模糊查询需要加%号
            predicate = predicate.and(qPerson.name.like("%" + personParam.getName() + "%"));
        }

        Optional<Integer> departmentIdOptional = Optional.ofNullable(personParam.getDepartmentId());
        predicate = predicate.and(
                departmentIdOptional.map(departmentId -> {
                    return qPerson.department.id.eq(personParam.getDepartmentId());
                }).orElse(null)
        );
        Page<Person> page = personRepository.findPage(predicate);
        List<PersonDTO> personDTOS = PersonMapper.MAPPER.entityToDTO(page.getContent());

        return PageDTO.of(page.getTotalElements(), personDTOS);
    }

    @Override
    public List<UserDTO> findAllUsableUser() {
        List<Integer> userIds = personRepository.findAllUserIds();
        List<User> userList = userRepository.findByStatusEqualsAndIdNotIn(UserStatus.NORMAL, userIds);
        return UserMapper.MAPPER.entityToDTO(userList);
    }

    @Override
    public PersonDTO getById(Integer id) {
        return PersonMapper.MAPPER.entityToDTO(personRepository.getOne(id));
    }

    @Override
    public PageDTO<UserDTO> getUserPage(PersonParam personParam) {
        //1、构造条件
        QPerson qPerson = QPerson.person;
        BooleanExpression predicate = qPerson.enable.eq(true);
        Optional<Integer> departmentIdOptional = Optional.ofNullable(personParam.getDepartmentId());
        predicate = predicate.and(
                departmentIdOptional.map(departmentId -> {
                    Department department = departmentRepository.findOne(personParam.getDepartmentId());
                    return qPerson.department.id.in(departmentRepository.getIdsById(department.getLft(), department.getRgt()));
                }).orElse(null)
        );

        //2、查询
        Page<Person> page = personRepository.findPage(predicate);
        List<User> userList = page.getContent().stream().map(person -> person.getUser()).collect(Collectors.toList());
        //3、转换
        List<UserDTO> userDTOList = UserMapper.MAPPER.entityToDTO(userList);
        //4、返回
        return PageDTO.of(page.getTotalElements(), userDTOList);
    }

    @Override
    public PageDTO<UserRoleDTO> getExitRoleUsers(Integer roleId, Integer departmentId) {
        //1、构造条件
        QPerson qPerson = QPerson.person;
        BooleanExpression predicate = qPerson.enable.eq(true);
        if (Objects.nonNull(departmentId)) {
            Department department = departmentRepository.findOne(departmentId);
            Role role = roleRepository.findOne(roleId);
            //查询条件是当前部门的已关联用户并是该角色的人员；
            predicate = predicate.and(qPerson.department.id.in(departmentRepository.getIdsById(department.getLft(), department.getRgt())))
                    .and(qPerson.user.id.isNotNull())
                    .and(qPerson.user.roles.contains(role));
        }
        //2、查询
        Page<Person> page = personRepository.findPage(predicate);
        List<User> userList = page.getContent().stream().map(person -> person.getUser()).collect(Collectors.toList());
        List<UserRoleDTO> userRoleDTOS = UserRoleMapper.MAPPER.entityToDTO(userList);
        return PageDTO.of(page.getTotalElements(), userRoleDTOS);
    }

    @Override
    public PageDTO<UserRoleDTO> getSetRoleUsers(Integer roleId, Integer departmentId) {
        //1、构造条件
        QPerson qPerson = QPerson.person;
        BooleanExpression predicate = qPerson.enable.eq(true);
        if (Objects.nonNull(departmentId)) {
            Department department = departmentRepository.findOne(departmentId);
            Role role = roleRepository.findOne(roleId);
            //查询条件是当前部门的已关联用户并且不是该角色的人员；
            predicate = predicate.and(qPerson.department.id.in(departmentRepository.getIdsById(department.getLft(), department.getRgt())))
                    .and(qPerson.user.id.isNotNull())
                    .and(qPerson.user.admin.isFalse())
                    .and(qPerson.user.roles.contains(role).not());
        }
        //2、查询
        Page<Person> page = personRepository.findPage(predicate);
        List<User> userList = page.getContent().stream().map(person -> person.getUser()).collect(Collectors.toList());
        List<UserRoleDTO> userRoleDTOS = UserRoleMapper.MAPPER.entityToDTO(userList);
        return PageDTO.of(page.getTotalElements(), userRoleDTOS);
    }

}
