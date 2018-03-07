package com.khcm.user.dao.entity.business.orgnization;

import com.khcm.user.dao.entity.base.BaseEntity;
import com.khcm.user.dao.entity.business.system.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 员工实体
 * <p>
 *
 * @author wangtao
 * @date 2017/9/15
 */
@Setter
@Getter
@ToString(exclude = {"organization", "department"})
@Entity
@Table(name = "t_person")
public class Person extends BaseEntity {
    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private Integer sex;

    @Column(name = "enable")
    private Boolean enable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Department department;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

}
