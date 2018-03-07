package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wangtao
 * @date 2017/12/28.
 */
@Getter
@Setter
public class ModuleVM extends TreeVM<ModuleVM> {

    private String url;
    private String icon;
    private String code;
    private String description;
    private List<OperationVM> operations;

}
