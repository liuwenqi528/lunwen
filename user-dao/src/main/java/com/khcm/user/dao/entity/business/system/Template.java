package com.khcm.user.dao.entity.business.system;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.common.enums.TempateType;
import com.khcm.user.common.enums.TemplateStatus;
import com.khcm.user.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-03-01.
 * Description:
 */
@Setter
@Getter
@Entity
@Table(name = "t_template")
public class Template extends BaseEntity {
    private static final long serialVersionUID = -686804261437373398L;
    /**
     * 模板名称
     */
    @Column(length = 50)
    private String tplName;

    /**
     * 模板类型：0短信、1邮箱、2APP推送消息
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "template_type")
    private TempateType templateType;

    /**
     * 业务类型： {登录、注册、找回密码}
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "business_type")
    private BusinessType businessType;

    /**
     * 模板内容
     */
    @Column
    private String content;

    /**
     * 模板状态：{0：已通过, 1：待审核, 2：已拒绝}
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TemplateStatus templateStatus;

    /**
     * 拒绝状态的回复信息
     */
    private String rejectReply;

    /**
     * 模板ID
     */
    @Column(name = "tpl_id")
    private Integer tplId;

    /**
     * 短信平台： 0、腾讯云   1、阿里云   等等
     */
    private Integer platform;

    /**
     * 备注
     */
    private String remark;
}
