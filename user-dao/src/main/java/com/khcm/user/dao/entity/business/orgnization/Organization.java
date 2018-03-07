package com.khcm.user.dao.entity.business.orgnization;

import com.khcm.user.dao.entity.base.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 机构实体
 *
 * @author wangtao
 * @date 2017/9/29
 */
@Setter
@Getter
@ToString(exclude = {"departments"})
@Entity
@Table(name = "t_organization")
public class Organization extends TreeEntity<Organization> {

    @Column(name = "full_name",length = 100)
    private String fullName;

    @Column
    private String description;

    @Column(name = "enable")
    private Boolean enable;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Department> departments;


}
