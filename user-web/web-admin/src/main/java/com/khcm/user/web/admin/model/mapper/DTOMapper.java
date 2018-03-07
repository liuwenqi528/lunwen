package com.khcm.user.web.admin.model.mapper;

import java.util.List;
import java.util.Set;

/**
 * Created by wangtao on 2018/1/11.
 */
public interface DTOMapper<DTO, VM, PM, PARAM> {

    VM dtoToVM(DTO dto);

    List<VM> dtoToVM(List<DTO> dtoList);

    Set<VM> dtoToVM(Set<DTO> dtoSet);

    PARAM pmToParam(PM pm);

    List<PARAM> pmToParam(List<PM> pmList);
}
