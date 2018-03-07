package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
public class ConfigTabDTO implements Serializable {

    private String description;

    private String type;

    private String title;

}
