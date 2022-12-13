/**
 * Created by : Alan Nascimento on 12/09/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.controllers;



import com.myapi.dto.CreateTeamRoleDTO;
import com.myapi.dto.TeamRolesDTO;
import com.myapi.security.services.TeamRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller bypassing JWT auth.
 */
@RestController
@RequestMapping("/api/noauth")
public class TeamRoleControllerNoAuth {


    @Autowired
    private TeamRoleService teamRoleService;

    @ApiOperation(value = "URI: /api/noauth/roles/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Create a role to be associated with a team member")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Role created successfully"),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.POST, value = "/roles/save")
    public ResponseEntity<TeamRolesDTO> save(@RequestBody CreateTeamRoleDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(teamRoleService.save(dto));
    }
    @ApiOperation(value = "URI: /api/noauth/roles",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "find all roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.GET, value = "/roles")
    public ResponseEntity<List<TeamRolesDTO>> find(){
           return ResponseEntity.status(HttpStatus.OK).body(teamRoleService.find());
    }

    @ApiOperation(value = "URI: /api/noauth/roles/role/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "find users by role name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server side exception"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    @RequestMapping (method = RequestMethod.GET, value = "/roles/role")
    public ResponseEntity<?> findByRoleName(@RequestParam (name = "RoleName") String roleName){
        return ResponseEntity.status(HttpStatus.OK).body(teamRoleService.findAllUsersByRole(roleName));
    }



}
