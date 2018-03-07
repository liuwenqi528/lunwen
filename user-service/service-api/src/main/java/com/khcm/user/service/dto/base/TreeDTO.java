package com.khcm.user.service.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TreeDTO<DTO extends TreeDTO> implements Serializable {

    private Integer id;
    private Date gmtCreate;
    private Date gmtModified;
    private String text;
    private Integer level;
    private List<DTO> children;
    private Integer parentId;
    private String parentName;

    /**
     * 添加子节点
     *
     * @param child
     */
    public void addChild(DTO child) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(child);
    }

    @Override
    public String toString() {
        return "TreeDTO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", text='" + text + '\'' +
                ", level=" + level +
                ", children=" + children +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
