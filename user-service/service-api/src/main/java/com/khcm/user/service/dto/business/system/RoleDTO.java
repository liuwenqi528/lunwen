package com.khcm.user.service.dto.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 角色信息 DTO
 *
 * @author wangtao
 * @date 2017/10/24
 */
@Getter
@Setter
@ToString
public class RoleDTO implements Serializable {

    private Integer id;

    private String name;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

    private List<Integer> userIds;

    private String description;

}
