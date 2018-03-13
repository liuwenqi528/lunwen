package com.khcm.user.dao.repository.master.organization;

import com.khcm.user.dao.entity.business.orgnization.Person;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 人员
 * <p>
 * Created by yangwb on 2017/12/4.
 */
public interface PersonRepository extends BaseRepository<Person, Integer> {
    @Query("select p.user.id from  Person p where  p.user.id is not null and p.enable = true ")
    List<Integer> findAllUserIds();

    @Modifying
    @Query("update Person p set p.enable = ?2 where p.id = ?1")
    public void updateEnable(Integer id,Boolean enable);
}
