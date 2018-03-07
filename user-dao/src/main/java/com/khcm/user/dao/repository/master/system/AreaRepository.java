package com.khcm.user.dao.repository.master.system;

import com.khcm.user.dao.entity.business.system.Area;
import com.khcm.user.dao.repository.BaseRepository;

import java.util.List;

/**
 * Created by rqn on 2018/1/12.
 */
public interface AreaRepository extends BaseRepository<Area, Integer> {
    List<Area> findAllByParentId(Integer pid);
}
