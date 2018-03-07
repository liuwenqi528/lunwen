package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Getter
@Setter
public class ConfigPM {

    private String type;

    private Map<String, String> items;
}
