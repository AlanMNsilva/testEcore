/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.security.services;

import com.myapi.dto.CreateUserRoleDTO;
import com.myapi.dto.TeamRolesDTO;
import com.myapi.dto.UserExternalDTO;
import com.myapi.dto.UserRoleDTO;
import com.myapi.exception.UserException;
import com.myapi.models.UserRole;
import com.myapi.repository.UserRoleRepository;
import com.myapi.services.exceptions.UserBadRequestException;
import com.myapi.services.exceptions.UserNotFoundException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;


@Service
@Transactional
public class UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TeamRoleService teamRoleService;

    @Autowired
    private ModelMapper modelMapper;



    public CreateUserRoleDTO save(CreateUserRoleDTO dto) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();

        fetchExternalUser(dto.getUserId());
        Boolean userHasRole = userRoleRepository.existsById(dto.getUserId());
        if(userHasRole){
            log.error("Role already associated to a user");
            throw new UserBadRequestException("The user id " + dto.getUserId() + "has already a role associated:");
        }
        userRoleDTO.setUserId(dto.getUserId());
        userRoleDTO.setTeamRoles(fetchRole(dto.getRoleName()));

        userRoleRepository.save(modelMapper.map(userRoleDTO, UserRole.class));
        return dto;

    }


    public TeamRolesDTO fetchRole(String roleName) {
       return teamRoleService.findByName(roleName);
    }

    public UserRoleDTO findUserById(String userId){
     return  modelMapper.map(userRoleRepository.findById(userId).orElseThrow(()
             -> new UserNotFoundException("User not found with gaven ID: " + userId)), UserRoleDTO.class);
    }

    public UserExternalDTO fetchExternalUser(String userId)  {

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/" + userId)
                    .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                throw new UserNotFoundException("Something went wrong with external request. Code:" + response.code());
            }
            UserExternalDTO userExternalDTO = modelMapper.map(response.body().byteStream(), UserExternalDTO.class);
            return userExternalDTO;
        } catch (IOException ex){
            throw new UserException("Something went wrong with external request: /n" + ex.getMessage());
        }

    }
}
