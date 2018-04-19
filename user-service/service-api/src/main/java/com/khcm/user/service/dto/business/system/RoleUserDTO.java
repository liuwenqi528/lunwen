package com.khcm.user.service.dto.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 角色用户信息 DTO
 * @author Liuwenqi
 * @date 2017/10/24
 */
@Getter
@Setter
@ToString
public class RoleUserDTO implements Serializable{

    private Integer id;

    private String name;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

    private String description;

    private List<Integer> userIds;

    private Set<AuthorizationDTO> authorizations;

}
