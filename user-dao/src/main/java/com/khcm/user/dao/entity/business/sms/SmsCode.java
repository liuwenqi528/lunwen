package com.khcm.user.dao.entity.business.sms;

import com.khcm.user.common.enums.SmsCodeType;
import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yangwb on 2018/2/27.
 */
@Setter
@Getter
@Entity
@Table(name = "t_sms_code")
public class SmsCode extends BaseEntity {

    @Column(length = 11)
    private String phone;

    @Column(length = 6)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send_time")
    private Date sendTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_time")
    private Date expiredTime;

    @Column(name = "send_count")
    private Integer sendCount;

    @Enumerated(EnumType.ORDINAL)
    private SmsCodeType type;

}
