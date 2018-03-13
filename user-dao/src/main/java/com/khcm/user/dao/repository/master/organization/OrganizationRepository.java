package com.khcm.user.dao.repository.master.organization;

import com.khcm.user.dao.entity.business.orgnization.Organization;
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

    @Query("select org.id from Organization org where org.lft>=?1 and org.rgt<=?2")
    public List<Integer> getIdsById(Integer lft, Integer rgt);
    public List<Organization> findByLftGreaterThanEqualAndRgtLessThanEqual(Integer lft, Integer rgt);

    @Modifying
    @Query("update Organization org set org.enable = ?2 where org.id = ?1")
    public void updateEnable(Integer id,Boolean enable);
}
