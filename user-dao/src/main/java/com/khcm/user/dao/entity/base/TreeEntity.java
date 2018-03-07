package com.khcm.user.dao.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yangwb on 2017/11/30.
 */
@Getter
@Setter
@ToString(exclude = {"parent", "children"})
@MappedSuperclass
public class TreeEntity<T extends TreeEntity> extends BaseEntity {

    @Column(length = 36, nullable = false)
    private String name;

    @Column
    private Integer lft;

    @Column
    private Integer rgt;

    @Column
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private T parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
    private List<T> children;

}
