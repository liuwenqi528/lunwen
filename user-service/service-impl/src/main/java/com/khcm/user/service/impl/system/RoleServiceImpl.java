package com.khcm.user.service.impl.system;

import com.khcm.user.dao.entity.business.system.QRole;
import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.master.system.RoleRepository;
import com.khcm.user.dao.repository.master.system.UserRepository;
import com.khcm.user.service.api.system.RoleService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.mapper.system.RoleMapper;
import com.khcm.user.service.mapper.system.RoleUserMapper;
import com.khcm.user.service.param.business.system.RoleParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 角色业务逻辑实现类
 *
 * @author wangtao
 * @date 2017/9/18
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public RoleDTO saveOrUpdate(RoleParam roleParam) {
        Optional<Integer> roleParamIdOptional = Optional.ofNullable(roleParam.getId());
        return roleParamIdOptional.map(roleParamId -> {
            Role role = roleRepository.findOne(roleParamId);
            RoleMapper.MAPPER.paramToEntity(roleParam, role);
            return RoleMapper.MAPPER.entityToDTO(roleRepository.save(role));
        }).orElse(RoleMapper.MAPPER.entityToDTO(roleRepository.save(RoleMapper.MAPPER.paramToEntity(roleParam))));
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.forEach(id -> roleRepository.delete(id));
    }

    @Override
    public RoleDTO getById(Integer id) {
        return RoleMapper.MAPPER.entityToDTO(roleRepository.findOne(id));
    }

    @Override
    public RoleUserDTO getRoleUserById(Integer id) {
        return RoleUserMapper.MAPPER.entityToDTO(roleRepository.findOne(id));
    }

    @Override
    public RoleDTO getOne(RoleParam roleParam) {
        QRole qRole = QRole.role;
        BooleanExpression predicate;
        if (StringUtils.isNotBlank(roleParam.getName())) {
            predicate = qRole.name.eq(roleParam.getName());
        } else {
            predicate = qRole.isNotNull();
        }
        Optional<Integer> roleParamIdOptional = Optional.ofNullable(roleParam.getId());
        predicate = predicate.and(roleParamIdOptional.map(roleParamId -> {
            return qRole.id.ne(roleParamId);
        }).orElse(null));
        Role role = Optional.ofNullable(roleRepository.findList(predicate)).filter(roles -> roles.size() > 0).map(roles -> roles.get(0)).orElse(null);
        return RoleMapper.MAPPER.entityToDTO(role);
    }

    @Override
    public List<RoleDTO> getList(RoleParam roleParam) {
        //1、构造条件
        QRole role = QRole.role;
        BooleanExpression p = role.isNotNull();

        //2、条件

        //3、返回
        return RoleMapper.MAPPER.entityToDTO(roleRepository.findList(p));
    }

    @Override
    public PageDTO<RoleDTO> getPage(RoleParam roleParam) {
        String name = roleParam.getName();
        //1、构造条件
        QRole role = QRole.role;
        BooleanExpression p;
        if (StringUtils.isNotBlank(name)) {
            p = role.name.like("%" + name + "%");
        } else {
            p = role.isNotNull();
        }
        //2、查询
        Page<Role> page = roleRepository.findPage(p);

        //3、转换
        List<RoleDTO> roleList = RoleMapper.MAPPER.entityToDTO(page.getContent());

        //4、返回
        return PageDTO.of(page.getTotalElements(), roleList);
    }

    @Override
    public void setUser(Integer roleId, List<Integer> userIdList) {
        Role role = roleRepository.findOne(roleId);
        role.getUsers().clear();
        userIdList.forEach(userId -> {
            User user = userRepository.findOne(userId);
            role.getUsers().add(user);
        });
        roleRepository.save(role);
    }


}
