package com.khcm.user.dao.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Repository使用规则：
 * 1、CRUD使用该类中的方法
 * 2、查询分页列表，复杂多条件询用该类中的方法，采用QueryDSL
 * 3、简单1，2个参数的特殊查询用JPA方法命名查询，具体参考JPA规范，命名findByUsernameIs:查询用户名为XXX的用户
 * 4、简单3个及以上参数的特殊查询用JPA方法注解HQL查询，例如@Query("select * from User u where u.username = ?1")
 *
 * @author wangtao
 * on 2017/8/19
 */

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 查询实体列表
     * @param predicate 条件
     * @return 列表
     */
    List<T> findList(Predicate predicate);
    List<T> findList(Predicate predicate, Sort sort);

    /**
     * 查询分页实体列表
     * @param predicate 条件
     * @return 分页信息
     */
    Page<T> findPage(Predicate predicate);

}
