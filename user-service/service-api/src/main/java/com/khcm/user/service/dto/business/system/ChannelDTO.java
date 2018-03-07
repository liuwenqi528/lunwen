package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class ChannelDTO implements Serializable {
    private Integer id;
    private String name;
    private String deliveryImageURL;
    private BigDecimal deliveryCost;
    private String description;
    private Date gmtCreate;
    private Date gmtModified;

}