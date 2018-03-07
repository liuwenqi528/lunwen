package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.common.enums.AreaType;
import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rqn on 2018/1/12.
 */
@Getter
@Setter
public class AreaPM  extends BasePM {
    private Integer id;
    private String name;
    private String code;
    private Integer parentId;
    private String description;
    private AreaType areaType;

}
