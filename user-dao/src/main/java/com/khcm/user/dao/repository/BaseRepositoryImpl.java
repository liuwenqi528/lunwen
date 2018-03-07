package com.khcm.user.dao.repository;

import com.khcm.user.common.context.PageContext;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义 Repository 的实现类，作为 Repository 代理的自定义类来执行,主要提供自定义的公用方法。
 * 父类 QueryDslJpaRepository 帮我们实现了 JpaRepository 中的方法，
 * <p>
 * 下一步需创建一个自定义工厂，在该工厂中注册自定义的 BaseRepositoryImpl，
 * 该工厂的具体写法参照 Spring Data 的 JpaRepositoryFactoryBean 和 JpaRepositoryFactory
 *
 * @author wangtao
 * 2017/8/19
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements BaseRepository<T, ID>, Serializable {

    private static final long serialVersionUID = -552345460226216122L;

    @PersistenceContext
    private final EntityManager entityManager;

    BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findList(Predicate predicate) {
        return findAll(predicate);
    }

    @Override
    public List<T> findList(Predicate predicate, Sort sort) {
        return findAll(predicate,sort);

    }

    @Override
    public Page<T> findPage(Predicate predicate) {
        // 注意：Spring Data 的分页是从第 0 页开始，所以 pageNo 要减一
        Pageable pageable = new PageRequest(PageContext.getPageNo() - 1, PageContext.getPageSize(), getSort());
        return findAll(predicate, pageable);
    }

    /**
     * 解析排序
     *
     * @return 排序
     */
    private Sort getSort() {
        String orderBy = PageContext.getOrderBy();
        if (StringUtils.isNotBlank(orderBy)) {
            String[] orderByArray = orderBy.split(",");
            return new Sort(Stream.of(orderByArray).map(item -> {
                String[] itemArray = item.split("=");
                return new Sort.Order(Sort.Direction.fromString(itemArray[1]), itemArray[0]);
            }).collect(Collectors.toList()));
        }
        return null;
    }

}
