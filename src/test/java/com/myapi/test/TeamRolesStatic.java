/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.test
 */
package com.myapi.test;

import com.myapi.dto.CreateTeamRoleDTO;
import com.myapi.models.TeamRoles;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class TeamRolesStatic {

    public static String anId() {
        return UUID.randomUUID().toString();
    }

    public static String aName() {
        return RandomStringUtils.randomAlphabetic(5, 20);
    }

    public static TeamRoles aCreateRole(){
       TeamRoles teamRoles = new TeamRoles();
       teamRoles.setRoleName("developer");
       teamRoles.setId(Long.valueOf(RandomStringUtils.randomNumeric(5,10)));
         return teamRoles;
    }

    public static CreateTeamRoleDTO aCreateDTO(){
        CreateTeamRoleDTO dto = new CreateTeamRoleDTO();
        dto.setRoleName(RandomStringUtils.randomAlphabetic(5, 20));
        return dto;
    }
}
