package com.khcm.user.dao.repository.master.organization;

import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 机构
 * <p>
 * Created by yangwb on 2017/12/4.
 */
public interface OrganizationRepository extends BaseRepository<Organization, Integer> {

    //    @Query(value = "select full_name.id from (select org FROM t_organization org) full_name,(select @pv :=?1 ) initialisation  where (FIND_IN_SET(parent_id,@pv)>0 and @pv := CONCAT(@pv, ',', id)) OR id = ?1",nativeQuery = true)
    //    public List<Integer> getIds(Integer id);
    //    @Query(value = "SELECT id FROM t_organization WHERE lft>=(SELECT lft  FROM t_organization WHERE id=?1) AND rgt <=(SELECT rgt  FROM t_organization WHERE id=?1)",nativeQuery = true)
    @Query("select org.id from Organization org where org.lft>=?1 and org.rgt<=?2")
    public List<Integer> getIdsById(Integer lft, Integer rgt);
    public List<Organization> findByLftGreaterThanEqualAndRgtLessThanEqual(Integer lft, Integer rgt);

    @Modifying
    @Query("update Organization org set org.enable = ?2 where org.id = ?1")
    public void updateEnable(Integer id,Boolean enable);
}
