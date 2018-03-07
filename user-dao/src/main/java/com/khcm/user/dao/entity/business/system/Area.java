package com.khcm.user.dao.entity.business.system;

import com.khcm.user.common.enums.AreaType;
import com.khcm.user.dao.entity.base.TreeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author wangtao
 * @date 2018/1/10.
 */
@Entity
@Table(name = "t_area")
@Setter
@Getter
public class Area extends TreeEntity<Area> {

    @Column(length = 6, unique = true)
    private String code;

    @Column(length = 100)
    private String description;

    private AreaType areaType;

}
