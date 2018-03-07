package com.khcm.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自定义 RepositoryFactoryBean 代替默认的 RepositoryFactoryBean，返回 RepositoryFactory，Spring Data Jpa 将使用 RepositoryFactory 来创建 Repository 的具体实现，
 * 用 BaseRepositoryImpl 代替 SimpleJpaRepository 作为 Repository 接口的实现，这样就能达到为所有 Repository 添加自定义方法的目的。
 *
 * @author wangtao
 * @date 2017/9/5
 */
public class BaseRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends
        JpaRepositoryFactoryBean<T, S, ID> {

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public BaseRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
     * Returns a RepositoryFactorySupport
     */
    @Override
    protected final RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new DefaultRepositoryFactory(entityManager);

    }

    /**
     * 创建一个内部类(Repository 工厂)，该类不用在外部访问，用于创建 Repository
     */
    private static class DefaultRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {

        /**
         * 构造器
         *
         * @param entityManager JPA entity manager.
         */
        public DefaultRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        /**
         * 设置具体的实现类为 BaseRepositoryImpl
         *
         * 注意：Spring Data JPA 是调用 SimpleJpaRepository 来创建实例
         *
         * @see JpaRepositoryFactory#getTargetRepository
         * (org.springframework.data.repository.core.RepositoryMetadata, javax.persistence.EntityManager)
         */
        @Override
        protected SimpleJpaRepository<T, ID> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return new BaseRepositoryImpl<>(getEntityInformation((Class<T>) information.getDomainType()), entityManager);
        }

        /**
         * 设置具体实现类的 Class
         *
         * @see JpaRepositoryFactory#getRepositoryBaseClass(RepositoryMetadata)
         */
        @Override
        protected final Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }

}
