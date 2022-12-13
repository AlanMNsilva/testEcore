/**
 * Created by : Alan Nascimento on 12/09/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.controllers;


import com.myapi.dto.CreateUserRoleDTO;
import com.myapi.dto.TeamRolesDTO;
import com.myapi.dto.UserRoleDTO;
import com.myapi.security.services.TeamRoleService;
import com.myapi.security.services.UserRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/noauth")
public class UserRoleControllerNoAuth {

    @Autowired
    private UserRoleService userRoleService;


    @ApiOperation(value = "URI: /api/noauth/roles/user/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Associate a role to a team member by fetching to the external API")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 500, message = "Server side exception"),
            @ApiResponse(code = 404, message = "User not found"),
    })
    @RequestMapping (method = RequestMethod.POST, value = "/roles/user/save")
    public ResponseEntity<CreateUserRoleDTO> save(@RequestBody @Valid CreateUserRoleDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.save(dto));
    }

    @ApiOperation(value = "URI: /api/noauth/roles/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "find user by filtering")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server side exception"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping("/roles/user/{id}")
    public ResponseEntity<?> find(@PathVariable("id") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userRoleService.findUserById(userId));
    }




}
