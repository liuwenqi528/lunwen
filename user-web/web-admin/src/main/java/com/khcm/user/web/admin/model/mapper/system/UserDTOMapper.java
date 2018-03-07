package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.common.utils.DateUtils;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.param.business.system.UserParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.UserPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.UserVM;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.Objects;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface UserDTOMapper extends DTOMapper<UserDTO, UserVM, UserPM, UserParam> {

    UserDTOMapper MAPPER = Mappers.getMapper(UserDTOMapper.class);

    @Override
    @Mapping(target = "beginTime", expression = "java(stringToDate(userPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(stringToDate(userPM.getEndTime()))")
    UserParam pmToParam(UserPM userPM);

    @Override
    @Mapping(target = "statusName", expression = "java(getStatusName(userDTO.getStatus()))")
    UserVM dtoToVM(UserDTO userDTO);

    default String getStatusName(UserStatus userStatus){
        String statusName;
        if (Objects.isNull(userStatus)) {
            return null;
        } else {
            switch (userStatus) {
                case NORMAL:
                    statusName = "NORMAL";
                    break;
                case DISABLED:
                    statusName = "DISABLED";
                    break;
                case LOCKED:
                    statusName = "LOCKED";
                    break;
                case UNACTIVED:
                    statusName = "UNACTIVED";
                    break;
                case CLOSED:
                    statusName = "CLOSED";
                    break;
                default:
                    statusName = "未标注";
            }
            return statusName;
        }
    }


    /**
     * @param stringParam
     * @return
     */
    default Date stringToDate(String stringParam) {
        if (StringUtils.isNotBlank(stringParam)) {
            Date date = DateUtils.getDateTimeFormat(stringParam);
            return date;
        } else {
            return null;
        }
    }
}
