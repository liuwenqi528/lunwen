package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @date
 */
@Getter
@Setter
@ToString
public class AppDTO implements Serializable {

    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private String code;

    private String url;

    private Integer priority;

    private String description;

}
