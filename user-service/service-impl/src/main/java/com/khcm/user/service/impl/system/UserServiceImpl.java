package com.khcm.user.service.impl.system;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.dao.entity.business.system.QUser;
import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.master.system.RoleRepository;
import com.khcm.user.dao.repository.master.system.UserRepository;
import com.khcm.user.service.api.system.ConfigService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.*;
import com.khcm.user.service.mapper.system.*;
import com.khcm.user.service.param.business.system.UserInfoParam;
import com.khcm.user.service.param.business.system.UserParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * 用户业务逻辑实现类
 *
 * @author wangtao
 * @date 2017/8/18
 */
@SuppressWarnings({"ALL", "MapOrSetKeyShouldOverrideHashCodeEquals"})
@Service("userService")
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConfigService configService;

    @Override
    public UserDTO saveOrUpdate(UserParam userParam) {
        //封装UserParam对象中的id属性。
        Optional<Integer> userParamId = Optional.ofNullable(userParam.getId());
        //返回保存后返回的对象。
        // 如果userParamId中的值不为null,则执行此代码块。
        UserDTO userDTO = userParamId.map(id -> {
            //因为参数对象中的id存在，说明是修改。 所以先从数据库中查询到原来的数据
            User user = userRepository.findOne(userParam.getId());
            //封装userParam对象中password属性
            Optional<String> userParamPassword = Optional.ofNullable(userParam.getPassword());
            //如果password属性为null,需要把数据库中的password赋值给UserParam.
            userParam.setPassword(userParamPassword.map(pwd -> pwd).orElse(user.getPassword()));
            //将参数UserParam中的非null数据赋值给user
            UserMapper.MAPPER.paramToEntity(userParam, user);
            return UserMapper.MAPPER.entityToDTO(userRepository.save(user));
        }).orElseGet(() -> {
            //id为null,说明是保存。
            //给新的用户赋一个初始状态。
            userParam.setStatus(UserStatus.NORMAL);
            return UserMapper.MAPPER.entityToDTO(userRepository.save(UserMapper.MAPPER.paramToEntity(userParam)));
        });

        return userDTO;
    }

    @Override
    public void remove(List<Integer> ids) {
        //删除用户，将状态改成关闭；
        ids.forEach(id -> userRepository.updateUserStatus(id, UserStatus.CLOSED));
    }

    @Override
    public UserDTO getById(Integer id) {
        return UserMapper.MAPPER.entityToDTO(userRepository.findOne(id));
    }


    @Override
    public List<UserDTO> getList(UserParam userParam) {
        //1、构造条件
        QUser qUser = QUser.user;
        BooleanExpression p = qUser.isNotNull();

        //2、返回
        return UserMapper.MAPPER.entityToDTO(userRepository.findList(p));
    }

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    @Override
    public Set<UserRoleDTO> getUserRoleList(Integer roleId) {
        Set<User> users = roleRepository.findOne(roleId).getUsers();
        @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals") Set<UserRoleDTO> userRoleDTOS = UserRoleMapper.MAPPER.entityToDTO(users);
        return userRoleDTOS;
    }

    @Override
    public Set<UserRoleDTO> getRoleSetUserList(Integer roleId, List<Integer> ids) {
        //封装ids，用于判断是否为null.
        Optional<List<Integer>> idsOptional = Optional.ofNullable(ids);
        //过滤--判断集合是否长度大于0.
        Optional<List<Integer>> idListOptional = idsOptional.filter(idList -> idList.size() > 0);

        List<User> users = idListOptional.map(idList ->
                userRepository.findByStatusEqualsAndAdminIsFalseAndIdNotIn(UserStatus.NORMAL, ids)
        ).orElse(
                userRepository.findByStatusEqualsAndAdminIsFalse(UserStatus.NORMAL)
        );

        List<UserRoleDTO> listUserDTo = UserRoleMapper.MAPPER.entityToDTO(users);
        return new HashSet<>(listUserDTo);
    }

    @Override
    public UserDTO changePwd(Integer uid, String pwd) {
        User user = userRepository.findOne(uid);
        user.setPassword(pwd);
        return UserMapper.MAPPER.entityToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO editUserInfo(UserInfoParam userInfoParam) {
        User user = userRepository.findOne(userInfoParam.getId());
        UserInfoMapper.MAPPER.paramToEntity(userInfoParam, user);
        return UserMapper.MAPPER.entityToDTO(userRepository.save(user));
    }



    /**
     * reason:使用like关键字时，仍需要加%号，才能起作用。
     *
     * @param userParam
     * @return
     */
    @Override
    public PageDTO<UserDTO> getPage(UserParam userParam) {
        //1、构造条件
        QUser qUser = QUser.user;
        BooleanExpression predicate = qUser.isNotNull();
        if (StringUtils.isNotBlank(userParam.getUsername())) {
            predicate = predicate.and(qUser.username.like("%" + userParam.getUsername() + "%"));
        }
        if (StringUtils.isNotBlank(userParam.getPhone())) {
            predicate = predicate.and(qUser.phone.like("%" + userParam.getPhone() + "%"));
        }
        if (StringUtils.isNotBlank(userParam.getEmail())) {
            predicate = predicate.and(qUser.email.like("%" + userParam.getEmail() + "%"));
        }
        if (StringUtils.isNotBlank(userParam.getRealname())) {
            predicate = predicate.and(qUser.ext.realname.like("%" + userParam.getRealname() + "%"));
        }
        Optional<Date> optionalBeginDate = Optional.ofNullable(userParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> qUser.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(userParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> qUser.gmtCreate.loe(endDate)).orElse(null));

        Optional<UserStatus> optionalUserStatus = Optional.ofNullable(userParam.getStatus());
        predicate = predicate.and(optionalUserStatus.map(userStatus -> qUser.status.eq(userStatus)).orElse(null));

        //2、查询
        Page<User> page = userRepository.findPage(predicate);

        //3、转换
        List<UserDTO> userDTOList = UserMapper.MAPPER.entityToDTO(page.getContent());

        //4、返回
        return PageDTO.of(page.getTotalElements(), userDTOList);
    }

    @Override
    public LoginDTO getByUsername(String username) {
        User user = userRepository.findByUsernameIs(username);
        return LoginMapper.MAPPER.entityToDTO(user);
    }

    @Override
    public LoginDTO getByEmail(String email) {
        User user = userRepository.findByEmailEquals(email);
        return LoginMapper.MAPPER.entityToDTO(user);
    }

    @Override
    public LoginDTO getByPhone(String phone) {
        User user = userRepository.findByPhoneEquals(phone);
        return LoginMapper.MAPPER.entityToDTO(user);
    }

    @Override
    public Integer errorRemaining(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        User user = userRepository.findByUsernameIs(username);
        if (user == null) {
            return null;
        }

        ConfigLoginDTO configLogin = configService.getLoginConfig();
        int errorInterval = configLogin.getErrorInterval() * 60 * 1000;
        int errorTimes = configLogin.getErrorTimes();

        long now = System.currentTimeMillis();
        Integer errorCount = user.getErrorCount();
        Date errorTime = user.getErrorTime();
        if (errorCount == null || errorCount <= 0
                || errorTime == null || errorTime.getTime() + errorInterval < now) {
            return errorTimes;
        }
        return errorTimes - errorCount;
    }

    @Override
    public void loginSuccess(String username, String ip) {
        User user = userRepository.findByUsernameIs(username);
        if (user == null) {
            return;
        }

        user.setLoginCount(user.getLoginCount() == null ? 1 : user.getLoginCount() + 1);
        user.setLoginIp(ip);
        user.setLoginTime(new Timestamp(System.currentTimeMillis()));

        user.setErrorCount(0);
        user.setErrorTime(null);
        user.setErrorIp(null);

        userRepository.save(user);
    }

    @Override
    public void loginFailaure(String username, String ip) {
        User user = userRepository.findByUsernameIs(username);
        if (user == null) {
            return;
        }

        Date now = new Timestamp(System.currentTimeMillis());

        ConfigLoginDTO configLogin = configService.getLoginConfig();
        int errorInterval = configLogin.getErrorInterval() * 60 * 1000;

        Date errorTime = user.getErrorTime();
        if (errorTime == null || errorTime.getTime() + errorInterval < now.getTime()) {
            user.setErrorTime(now);
            user.setErrorCount(1);
        } else {
            user.setErrorCount(user.getErrorCount() + 1);
        }

        user.setErrorIp(ip);
        userRepository.save(user);
    }

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    @Override
    public void setRole(Integer userId, List<Integer> roleIdList) {
        User user = userRepository.findOne(userId);
        user.getRoles().clear();
        roleIdList.forEach(roleId -> {
            Role role = roleRepository.findOne(roleId);
            user.getRoles().add(role);
        });
        userRepository.save(user);
    }

}
