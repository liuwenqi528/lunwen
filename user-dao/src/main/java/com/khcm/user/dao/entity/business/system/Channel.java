package com.khcm.user.dao.entity.business.system;

import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 注册渠道
 * @author wangtao
 * @date 2018/1/10.
 */
@Entity
@Table(name = "t_channel")
@Setter
@Getter
public class Channel extends BaseEntity{

    @Column(length = 32, unique = true)
    private String name;

    @Column(length = 200,unique = true)
    private String deliveryImageURL;

    private BigDecimal deliveryCost;

    private String description;

}
