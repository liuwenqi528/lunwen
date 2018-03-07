package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ConfigParam {

    private String type;

    private Map<String, String> items;
}
