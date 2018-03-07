package com.khcm.user.dao.repository.master.organization;

import com.khcm.user.dao.entity.business.orgnization.Department;
import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 部门
 * <p>
 * Created by yangwb on 2017/12/4.
 */
public interface DepartmentRepository extends BaseRepository<Department, Integer> {

    @Query("select dept.id from Department dept where dept.lft>=?1 and dept.rgt<=?2")
    public List<Integer> getIdsById(Integer lft, Integer rgt);

    public List<Department> findByLftGreaterThanEqualAndRgtLessThanEqual(Integer lft, Integer rgt);

    @Modifying
    @Query("update Department dept set dept.enable = ?2 where dept.id = ?1")
    public void updateEnable(Integer id,Boolean enable);
}
