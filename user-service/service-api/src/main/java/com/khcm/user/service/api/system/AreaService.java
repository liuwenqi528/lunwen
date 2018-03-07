package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.param.business.system.AreaParam;

import java.util.List;

/**
 * Created by rqn on 2018/1/12.
 */
public interface AreaService {

    AreaDTO saveOrUpdate(AreaParam areaParam);

    List<AreaDTO> getList();

    AreaDTO getById(Integer id);

    void remove(List<Integer> ids);

    List<AreaDTO> findAllByParentId(Integer pid);

    /**
     * @author Liuwenqi
     * 根据条件判断是否已存在相同数据
     * @param areaParam
     * @return
     */
    AreaDTO getOne(AreaParam areaParam);
}
