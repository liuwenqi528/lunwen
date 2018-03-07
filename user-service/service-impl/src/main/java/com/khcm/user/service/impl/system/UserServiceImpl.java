package com.khcm.user.service.impl.system;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.dao.entity.business.system.*;
import com.khcm.user.dao.entity.business.system.QUser;
import com.khcm.user.dao.repository.master.system.AreaRepository;
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

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public UserDTO saveOrUpdate(UserParam userParam) {
        if (Objects.isNull(userParam.getId())) {
            userParam.setStatus(UserStatus.NORMAL);
            return UserMapper.MAPPER.entityToDTO(userRepository.save(UserMapper.MAPPER.paramToEntity(userParam)));
        }
        User user = userRepository.findOne(userParam.getId());
        if (Objects.isNull(userParam.getPassword())) {
            userParam.setPassword(user.getPassword());
        }
        UserMapper.MAPPER.paramToEntity(userParam, user);
        return UserMapper.MAPPER.entityToDTO(userRepository.save(user));
    }

    @Override
    public void remove(List<Integer> ids) {
        //删除用户，将状态改成关闭；
        ids.forEach(id -> userRepository.updateUserStatus(id,UserStatus.CLOSED));
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
        List<User> users =null;
        if(Objects.nonNull(ids)&& !ids.isEmpty()){
            users = userRepository.findByStatusEqualsAndAdminIsFalseAndIdNotIn(UserStatus.NORMAL, ids);
        }else{
            users = userRepository.findByStatusEqualsAndAdminIsFalse(UserStatus.NORMAL);
        }

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
        if (Objects.nonNull(userInfoParam.getAreaId())) {
            user.getExt().setArea(areaRepository.findOne(userInfoParam.getAreaId()));
        }
        UserInfoMapper.MAPPER.paramToEntity(userInfoParam, user);
        return UserMapper.MAPPER.entityToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserAndArea(Integer uid) {
        User user = userRepository.findOne(uid);
        UserDTO userDTO = UserMapper.MAPPER.entityToDTO(user);
        if (Objects.nonNull(user.getExt().getArea())) {
            Integer areaPid = user.getExt().getArea().getId();
            List<AreaDTO> list = new ArrayList<>();
            list = arrangement(areaPid, list);
            userDTO.setAreaDTOList(list);
        }
        return userDTO;
    }

    public List<AreaDTO> arrangement(Integer id, List<AreaDTO> list) {
        Area area = areaRepository.findOne(id);
        list.add(AreaMapper.MAPPER.entityToDTO(area));
        if (Objects.nonNull(area.getParent())) {
            arrangement(area.getParent().getId(), list);
        }
        return list;
    }

    /**
     * update by Qimeng Duan
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
        if(StringUtils.isNotBlank(userParam.getUsername())){
            predicate = predicate.and(qUser.username.like("%"+userParam.getUsername()+"%"));
        }
        if(StringUtils.isNotBlank(userParam.getPhone())){
            predicate = predicate.and(qUser.phone.like("%"+userParam.getPhone()+"%"));
        }
        if(StringUtils.isNotBlank(userParam.getEmail())){
            predicate = predicate.and(qUser.email.like("%"+userParam.getEmail()+"%"));
        }
        if(StringUtils.isNotBlank(userParam.getRealname())){
            predicate = predicate.and(qUser.ext.realname.like("%"+userParam.getRealname()+"%"));
        }
        if(StringUtils.isNotBlank(userParam.getChannelName())){
            predicate = predicate.and(qUser.ext.channel.name.like("%"+userParam.getChannelName()+"%"));
        }
        Optional<Date> optionalBeginDate = Optional.ofNullable(userParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> qUser.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(userParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> qUser.gmtCreate.loe(endDate)).orElse(null));

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
            //noinspection MapOrSetKeyShouldOverrideHashCodeEquals
            user.getRoles().add(role);
        });
        userRepository.save(user);
    }

}
