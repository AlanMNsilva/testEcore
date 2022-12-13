/**
 * Created by : Alan Nascimento on 12/09/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDTO {

    private String userId;

    private TeamRolesDTO teamRoles;

}
