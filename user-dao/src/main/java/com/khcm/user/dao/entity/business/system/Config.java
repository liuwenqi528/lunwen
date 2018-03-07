package com.khcm.user.dao.entity.business.system;

import com.khcm.user.common.enums.ConfigType;
import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Map;

/**
 * 系统配置实体
 *
 * @author wangtao
 * @date 2017/12/29
 */
@Setter
@Getter
@Entity
@Table(name = "t_config")
public class Config extends BaseEntity {

    @Column
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private ConfigType type;

    @Column(length = 20)
    private String title;

    /**
     * 排序使用，用于页面固定tab标题和内容顺序使用的。
     */
    @Column(name = "sort")
    private Integer sort;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "t_config_item")
    @MapKeyColumn(name = "item_name")
    @Column(name = "item_value")
    private Map<String, String> items;
}


