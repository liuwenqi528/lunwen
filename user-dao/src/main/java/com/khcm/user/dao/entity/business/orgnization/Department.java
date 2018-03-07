package com.khcm.user.dao.entity.business.orgnization;

import com.khcm.user.dao.entity.base.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 部门实体
 * <p>
 *
 * @author wangtao
 * @date 2017/9/5
 */
@Setter
@Getter
@ToString(exclude = {"organization"})
@Entity
@Table(name = "t_department")
public class Department extends TreeEntity<Department> {

    @Column(name = "full_name",length = 60)
    private String fullName;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Organization organization;

    @Column(name = "enable")
    private Boolean enable;
}
