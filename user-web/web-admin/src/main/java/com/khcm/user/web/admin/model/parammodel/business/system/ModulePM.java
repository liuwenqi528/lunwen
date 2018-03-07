package com.khcm.user.web.admin.model.parammodel.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Setter
@Getter
public class ModulePM {

    private String url;
    private String icon;
    private String code;
    private String description;
    private List<OperationPM> operations;

    public void addOperation(OperationPM operation) {
        if (operations == null) {
            operations = new ArrayList<>();
        }

        operations.add(operation);
    }
}
