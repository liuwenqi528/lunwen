package com.khcm.user.dao.entity.business.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khcm.user.dao.entity.base.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 权限表，包括模块与功能
 * Created by yangwb on 2018/1/2.
 */
@Setter
@Getter
@ToString(exclude = {"app"})
@Entity
@Table(name = "t_resource")
public class Resource extends TreeEntity<Resource> {

    @Column
    private String url;

    @Column(length = 30)
    private String icon;

    @Column(length = 100)
    private String code;

    @Column
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private App app;

    public enum ResourceType {
        /**
         * MODULE：菜单；OPERATION：操作
         */
        MODULE, OPERATION
    }
}
