package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by rqn
 */
@Getter
@Setter
public class ChannelVM {
    private Integer id;
    private String name;
    private String deliveryImageURL;
    private BigDecimal deliveryCost;
    private String description;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

}
