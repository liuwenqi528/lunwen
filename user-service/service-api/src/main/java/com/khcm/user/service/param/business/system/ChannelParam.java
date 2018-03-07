package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rqn
 */
@Getter
@Setter
public class ChannelParam {
    private Integer id;
    private String name;
    private String deliveryImageURL;
    private BigDecimal deliveryCost;
    private String description;
    private Date beginTime;
    private Date endTime;
}
