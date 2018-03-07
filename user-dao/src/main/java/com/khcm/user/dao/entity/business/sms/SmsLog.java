package com.khcm.user.dao.entity.business.sms;

import com.khcm.user.common.enums.SmsCodeType;
import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yangwb on 2018/3/1.
 */
@Setter
@Getter
@Entity
@Table(name = "t_sms_log")
public class SmsLog extends BaseEntity {

    @Column(length = 11)
    private String phone;

    @Column
    private String content;

    @Column
    private Integer status;

    @Enumerated(EnumType.ORDINAL)
    private SmsCodeType type;

    @Column
    private String remarks;

}
