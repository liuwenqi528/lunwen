package com.khcm.user.web.admin.shiro;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Created by yangwb on 2017/12/4.
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class Principal implements Serializable {

    @NonNull
    private Integer userId;

    @NonNull
    private String username;

    private Boolean admin;

}
