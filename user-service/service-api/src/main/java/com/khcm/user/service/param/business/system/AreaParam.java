package com.khcm.user.service.param.business.system;

import com.khcm.user.common.enums.AreaType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rqn on 2018/1/12.
 */
@Getter
@Setter
@Data
public class AreaParam {
    private Integer id;
    private String name;
    private String code;
    private Integer parentId;
    private String description;
    private AreaType areaType;
}
