package com.khcm.user.dao.entity.business.system;

import com.khcm.user.common.enums.PlatformEnum;
import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 用户表
 *
 * @author wangtao
 * @date 2017/6/5
 */
@Setter
@Getter
@ToString(exclude = {"ext", "roles"})
@EqualsAndHashCode(exclude = {"ext","roles"}, callSuper = false)
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -686804261437373398L;
    @Column(length = 36, nullable = false, unique = true)
    private String username;

    @Column(length = 36, unique = true)
    private String email;

    @Column(length = 12, unique = true)
    private String phone;

    @Column(length = 32, nullable = false)
    private String password;

    @Column(length = 32)
    private String salt;


    @Column(name = "is_admin")
    private Boolean admin;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    private Date loginTime;

    @Column(name = "last_login_ip")
    private String loginIp;

    @Column(name = "login_count")
    private Integer loginCount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "platform")
    private PlatformEnum loginPlatform;

    @Column(name = "login_device_id", length = 64)
    private String loginDeviceId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "error_time")
    private Date errorTime;

    @Column(name = "error_ip")
    private String errorIp;

    @Column(name = "error_count")
    private Integer errorCount;

    @Column(name = "register_ip")
    private String registerIp;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "ext_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private UserExt ext;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))}
    )
    private Set<Role> roles;

}
