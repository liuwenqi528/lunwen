package com.khcm.user.dao.entity.business.log;

import com.khcm.user.dao.entity.base.BaseEntity;
import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.User;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author wangtao
 */
@Getter
@Setter
@ToString(exclude = {"app"})
@Entity
@Table(name = "t_log")
public class Log extends BaseEntity {
    private static final long serialVersionUID = 2723018698785592676L;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "log_type")
    private LogType logType;

    @Column(length = 64)
    private String title;

    @Column
    private String content;

    @Column(length = 20)
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private App app;

    public enum LogType {
        /**
         * LOGIN_SUCCESS：登录成功；LOGIN_FAILURE：登录失败；OPERATION：操作日志
         */
        LOGIN_SUCCESS, LOGIN_FAILURE, OPERATION
    }
}
