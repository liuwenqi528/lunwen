package com.khcm.user.dao.entity.business.system;

import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色表
 * @author wangtao
 * @date 2017/8/15
 */
@Setter
@Getter
@ToString(exclude = {"users","authorizations"})
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {

    @Column(length = 36, name = "name", unique = true)
    private String name;

    @Column
    private Integer priority;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "t_user_role",
             inverseJoinColumns= {@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            joinColumns = {@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))}
    )
    private Set<User> users;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Authorization> authorizations;

}
