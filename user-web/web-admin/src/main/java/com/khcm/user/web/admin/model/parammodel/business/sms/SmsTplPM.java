package com.khcm.user.web.admin.model.parammodel.business.sms;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-03-01.
 * Description:
 */
@Getter
@Setter
public class SmsTplPM extends BasePM {


    private Integer id;
    private String beginTime;
    private String endTime;

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
     * 模板状态：{0：已通过, 1：待审核, 2：已拒绝}
     */
    private String templateStatus;

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
