/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.security.services;


import com.amazonaws.services.kms.model.NotFoundException;
import com.myapi.dto.CreateTeamRoleDTO;
import com.myapi.dto.TeamRolesDTO;
import com.myapi.dto.UserRoleDTO;
import com.myapi.models.TeamRoles;
import com.myapi.models.UserRole;
import com.myapi.repository.TeamRoleRepository;

import com.myapi.services.exceptions.UserBadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamRoleService {

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TeamRolesDTO save(CreateTeamRoleDTO dto){
       if(roleExists(dto.getRoleName())){
           throw  new UserBadRequestException("Role already exists with the given name:" + dto.getRoleName());
       }
       TeamRoles teamRoles = teamRoleRepository.save(modelMapper.map(dto, TeamRoles.class));
       TeamRolesDTO  teamRolesDTO = new TeamRolesDTO();
       teamRolesDTO.setId(teamRoles.getId());
       teamRolesDTO.setRoleName(teamRoles.getRoleName());
       return teamRolesDTO;
    }

    public Boolean roleExists(String roleName){
        TeamRoles tr = teamRoleRepository.findByRoleName(roleName).orElse(null);
        if(tr != null)
            return true;
        else
            return false;
    }

    public TeamRolesDTO findByName(String roleName){
        return  convert(teamRoleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new NotFoundException("Role Not Found")));
    }

    public List<UserRoleDTO> findAllUsersByRole(String roleName){
        List<UserRoleDTO> userRoleDTO = new ArrayList<>();
        List<UserRole> users =  teamRoleRepository.findAllUsersByRoleName(roleName);
        users.forEach(userRole -> userRoleDTO.add(modelMapper.map(userRole, UserRoleDTO.class)));
        return userRoleDTO;
    }

    public List<TeamRolesDTO> find(){
        List<TeamRoles> teamRoles = teamRoleRepository.findAll();
        List<TeamRolesDTO> teamRolesDTOS = new ArrayList<>();
        teamRoles.forEach(teamrole -> teamRolesDTOS.add(convert(teamrole)));
        return teamRolesDTOS;
    }

    private TeamRolesDTO convert(TeamRoles teamRoles){
        TeamRolesDTO teamRolesDTO = new TeamRolesDTO();
        teamRolesDTO.setRoleName(teamRoles.getRoleName());
        teamRolesDTO.setId(teamRoles.getId());
        return teamRolesDTO;
    }

}
