package com.khcm.user.web.admin.model.viewmodel.base;


import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
public class TreeVM<VM extends TreeVM> {

    private Integer id;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;
    private Integer level;
    private String text;
    private List<VM> children;
    private Integer parentId;
    private String parentName;

}
