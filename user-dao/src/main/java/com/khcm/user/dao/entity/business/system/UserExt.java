package com.khcm.user.dao.entity.business.system;

import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户扩展信息
 *
 * @author wangtao
 * on 2017/9/15
 */
@Setter
@Getter
@ToString(exclude = {"user"})
@Entity
@Table(name = "t_user_ext")
public class UserExt extends BaseEntity {

    @Column(length = 36)
    private String nickname;

    @Column(length = 36)
    private String realname;

    @Column(length = 255)
    private String avatar;

    @Column(length = 20)
    private String idcard;

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column
    private Integer age;

    @OneToOne(mappedBy = "ext")
    private User user;

    public enum Sex {
        /**
         * MALE:男；FEMALE：女；UNKOWN：未知（保密）
         */
        MALE, FEMALE, UNKOWN
    }
}
