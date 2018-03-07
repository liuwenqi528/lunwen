package com.khcm.user.dao.repository.master.system;

import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.repository.BaseRepository;
import com.querydsl.core.types.Predicate;

/**
 * 系统
 *
 * Created by yangwb on 2017/12/4.
 */
public interface AppRepository extends BaseRepository<App, Integer> {
    /**
     * 根据系统编码获取系统对象
     * @param appCode
     * @return
     */
    App findByCodeIs(String appCode);

    /**
     *
     * @param app
     * @return
     */
//    App find
}
