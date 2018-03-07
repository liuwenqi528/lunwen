package com.khcm.user.dao.repository.master.system;

import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.repository.BaseRepository;

import java.util.List;

/**
 * 权限
 * <p>
 * Created by yangwb on 2017/12/4.
 */
public interface ResourceRepository extends BaseRepository<Resource, Integer> {

    /**
     * 获取APP下所有的 Resource
     */
    List<Resource> findByAppCodeOrderByLft(String appCode);
}
