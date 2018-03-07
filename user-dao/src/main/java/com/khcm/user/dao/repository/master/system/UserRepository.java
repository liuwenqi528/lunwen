package com.khcm.user.dao.repository.master.system;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户
 * <p>
 * Created by yangwb on 2017/12/4.
 */
public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUsernameIs(String username);

    List<User> findByStatusEqualsAndIdNotIn(Enum userStatus, List<Integer> userIds);


    List<User> findByStatusEqualsAndAdminIsFalse(Enum userStatus);

    List<User> findByStatusEqualsAndAdminIsFalseAndIdNotIn(Enum userStatus, List<Integer> userIds);
    User findByEmailEquals(String email);
    User findByPhoneEquals(String phone);

    @Modifying
    @Query("update User u set u.status = ?2 where u.id =?1")
    void updateUserStatus(Integer id, UserStatus userStatus);

}
