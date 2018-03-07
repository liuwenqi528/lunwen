package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
public class ConfigDTO implements Serializable {

    private String type;

    private String title;

    private Map<String, String> items;
}
