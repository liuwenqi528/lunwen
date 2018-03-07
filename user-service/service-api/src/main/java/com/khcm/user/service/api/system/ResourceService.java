package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.param.business.system.ResourceParam;

import java.util.List;

public interface ResourceService {

    ResourceDTO saveOrUpdate(ResourceParam resourceParam);

    void remove(List<Integer> ids);

    ResourceDTO getById(Integer id);

    List<ResourceDTO> getList(ResourceParam resourceParam);

    ResourceDTO getOne(ResourceParam resourceParam);
}
