package com.khcm.user.dao.repository.master.system;

import com.khcm.user.dao.entity.business.system.Authorization;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 授权
 * Created by
 * @author yangwb on
 * @date 2017/12/4.
 */
public interface AuthorizationRepository extends BaseRepository<Authorization, Integer> {

    /**
     * 根据使用的系统编码和登录的用户编号查询可操作资源
     * @param appCode
     * @param userId
     * @return
     */
    @Query("select auth.resource from Authorization auth join auth.app join auth.resource join auth.role.users user where auth.app.code = ?1 and user.id = ?2")
    List<Resource> getResourcesByUserId(String appCode, Integer userId);

    /**
     * 根据系统编号和角色编号查询权限
     * @param appId
     * @param roleId
     * @return
     */
    @Query("select auth from Authorization auth join auth.app join auth.role  where auth.app.id = ?1 and auth.role.id = ?2")
    List<Authorization> findByAppIdAndByRoleId(Integer appId, Integer roleId);

    /**
     * 根据系统编号和角色编号删除权限
     * @param appId
     * @param roleId
     */
    @Modifying
    @Query("delete from Authorization auth  where auth.app.id = ?1 and auth.role.id = ?2")
    void deleteByAppIdAndByRoleId(Integer appId, Integer roleId);

    /**
     * 根据系统资源编号删除
     * @param resourceId
     */
    @Modifying
    @Query("delete from Authorization auth  where auth.resource.id = ?1")
    void deleteByResourceId(Integer resourceId);
    /**
     * 根据系统编号删除
     * @param appId
     */
    @Modifying
    @Query("delete from Authorization auth  where auth.app.id = ?1")
    void deleteByAppId(Integer appId);
}
