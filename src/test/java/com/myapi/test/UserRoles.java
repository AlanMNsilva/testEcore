/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.test
 */
package com.myapi.test;

import com.myapi.dto.CreateUserRoleDTO;
import com.myapi.dto.UserExternalDTO;
import com.myapi.models.TeamRoles;
import com.myapi.models.UserRole;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class UserRoles {

    public static String anId() {
        return UUID.randomUUID().toString();
    }

    public static String aName() {
        return RandomStringUtils.randomAlphabetic(5, 20);
    }

    public static CreateUserRoleDTO aCreateDTO(){

        CreateUserRoleDTO dto = new CreateUserRoleDTO();
        dto.setUserId(anId());
        dto.setRoleName("developer");
        return dto;

    }

    public static UserRole aCreateUser(){
        UserRole us = new UserRole();
        us.setId(anId());
        return us;
    }

    public static UserExternalDTO aCreateExternalDTO(){
        UserExternalDTO dto = new UserExternalDTO();
        dto.setId(anId());
        dto.setDisplayName(aName());
        return  dto;
    }

}
