package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.common.utils.DateUtils;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.system.UserInfoParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.UserPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.UserVM;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rqn
 * Date: 2018/2/1
 * Time: 18:35
 */
@Mapper
public interface UserInfoDTOMapper extends DTOMapper<UserDTO, UserVM, UserPM, UserInfoParam> {

    UserInfoDTOMapper MAPPER = Mappers.getMapper(UserInfoDTOMapper.class);

    @Override
    @Mapping(target = "birthday", expression = "java(stringToDate(userPM.getBirthday()))")
    UserInfoParam pmToParam(UserPM userPM);

    /**
     * update for Qimeng Duan
     * reason:传进来的是String类型，因此不建议使用Objects.nonNull方法。此处抛出了异常，故做出修改
     *
     * @param stringParam
     * @return
     */
    default Date stringToDate(String stringParam) {
        if (StringUtils.isNotBlank(stringParam)) {
            Date date = DateUtils.getDateFormat(stringParam);
            return date;
        } else {
            return null;
        }
    }
}
