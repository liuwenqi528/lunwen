package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by rqn on 2018/1/12.
 */
@Getter
@Setter
public class AreaVM extends TreeVM<AreaVM> {
    private Integer id;
    private String code;
    private Integer parentId;
    private String description;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;
    private String areaTypeName;
}
