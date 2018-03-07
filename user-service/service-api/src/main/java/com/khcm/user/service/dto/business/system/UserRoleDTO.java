package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author rqn
 *
 */

@Getter
@Setter
@ToString
public class UserRoleDTO implements Serializable {
    private Integer id;
    private String username;


}
