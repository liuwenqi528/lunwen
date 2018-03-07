package com.khcm.user.service.dto.business.system;

import com.khcm.user.service.dto.base.TreeDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class ModuleDTO extends TreeDTO<ModuleDTO> implements Serializable {

    private String url;
    private String icon;
    private String code;
    private String description;
    private List<OperationDTO> operations;

    public void addOperation(OperationDTO operation) {
        if (operations == null) {
            operations = new ArrayList<>();
        }

        operations.add(operation);
    }

    @Override
    public String toString() {
        return "ModuleDTO{" +
                "url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", operations=" + operations +
                '}';
    }
}
