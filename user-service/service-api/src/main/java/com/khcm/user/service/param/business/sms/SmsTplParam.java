package com.khcm.user.service.param.business.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-03-01.
 * Description:
 */
@Setter
@Getter
@ToString
public class SmsTplParam {

    private Integer id;

    private Date beginTime;

    private Date endTime;
    /**
     * 模板名称
     */
    private String tplName;

    /**
     * 短信业务类型：1注册、2修改密码、3找回密码
     */
    private String businessType;

    /**
     * 短信类型： {0：普通短信, 1：营销短信}
     */
    private Integer type;

    /**
     * 模板内容
     */
    private String content;
    /**
     * 模板ID(腾讯云返回的)
     */
    private Integer tplId;

    /**
     * 模板状态：{0：已通过, 1：待审核, 2：已拒绝}
     */
    private String templateStatus;

    /**
     * 拒绝状态的回复信息
     */
    private String rejectReply;

    /**
     * 短信平台： 1、腾讯云   2、阿里云   等等
     */
    private Integer platform;
    /**
     * 备注
     */
    private String remark;
    private String templateType;
}
