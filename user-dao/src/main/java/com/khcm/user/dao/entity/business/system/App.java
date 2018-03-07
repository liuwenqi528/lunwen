package com.khcm.user.dao.entity.business.system;

import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 系统表
 * Created by yangwb on 2017/11/30.
 */
@Setter
@Getter
@ToString(exclude = {"permissions"})
@Entity
@Table(name = "t_app")
public class App extends BaseEntity {

    @Column(length = 20, nullable = false, unique = true)
    private String code;

    @Column(length = 36, nullable = false)
    private String name;

    @Column
    private String url;

    @Column
    private Integer priority;

    @Column
    private String description;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Resource> permissions;

}
