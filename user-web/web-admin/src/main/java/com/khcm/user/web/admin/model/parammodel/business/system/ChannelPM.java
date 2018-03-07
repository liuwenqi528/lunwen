package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * Created by rqn
 */
@Getter
@Setter
public class ChannelPM  extends BasePM{
    private Integer id;
    private String name;
    private String deliveryImageURL;
    private BigDecimal deliveryCost;
    private String description;
    private String beginTime;

    private String endTime;
}
