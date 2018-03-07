package com.khcm.user.web.admin.model.viewmodel.business.system;


import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lwq
 * @date 2018/1/4.
 */
@Getter
@Setter
public class ResourceVM extends TreeVM<ResourceVM> {

    private Integer id;
    private String url;
    private String icon;
    private String code;
    private String resourceType;
    private String description;
    private Integer appId;
}
