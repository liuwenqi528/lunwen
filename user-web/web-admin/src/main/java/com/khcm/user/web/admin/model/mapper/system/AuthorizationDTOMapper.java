package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.dto.business.system.AuthorizationDTO;
import com.khcm.user.service.param.business.system.AuthorizationParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AuthorizationPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AuthorizationVM;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface AuthorizationDTOMapper extends DTOMapper<AuthorizationDTO, AuthorizationVM, AuthorizationPM, AuthorizationParam> {
    AuthorizationDTOMapper MAPPER = Mappers.getMapper(AuthorizationDTOMapper.class);

    @Override
    @Mapping(target = "resourceIds", expression = "java(getList(pm.getResourceIds()))")
    AuthorizationParam pmToParam(AuthorizationPM pm);
    default List<Integer> getList(String resourceId) {
        List<Integer> resourceIds = new ArrayList<>();
        if (StringUtils.isNotBlank(resourceId)) {
            resourceIds = Arrays.stream(resourceId.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
        }
        return resourceIds;
    }
}
