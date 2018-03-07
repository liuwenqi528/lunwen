package com.khcm.user.web.admin.model.viewmodel.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Setter
@Getter
public class ConfigVM {

    private String type;

    private String title;

    private Map<String, String> items;
}
