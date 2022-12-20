/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.controllers;



import com.myapi.dto.CreateUserRoleDTO;

import com.myapi.models.TeamRoles;
import com.myapi.models.UserRole;
import com.myapi.repository.TeamRoleRepository;
import com.myapi.repository.UserRoleRepository;
import com.myapi.test.TeamRolesStatic;
import com.myapi.test.TestUtil;
import com.myapi.test.UserRoles;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserRoleControllerTest")
@Transactional
public class UserRoleControllerIT  {

    private static final String API_URL = "/api/noauth/roles/user";



    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Autowired
    protected MockMvc restMockMvc;

    private TeamRoles staticTeamRole;

    private UserRole staticUserRole;

    @BeforeEach
    public void setup(){
        staticTeamRole = teamRoleRepository.saveAndFlush(TeamRolesStatic.aCreateRole());
        UserRole ur = UserRoles.aCreateUser();
        ur.setRoles(staticTeamRole);
        staticUserRole = userRoleRepository.saveAndFlush(ur);
    }
    @AfterEach
    public void clear() {
        userRoleRepository.deleteAll();
        teamRoleRepository.deleteAll();

    }

    @Nested
    public class WhenCreating {


        private final CreateUserRoleDTO createUserRoleDTO = UserRoles.aCreateDTO();


       @Test
        public void testShouldAssociateAUserToARole() throws Exception {

          createUserRoleDTO.setUserId("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c");
            createUserRole(createUserRoleDTO)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.userId").value(createUserRoleDTO.getUserId()))
                    .andExpect(jsonPath("$.roleName").value(createUserRoleDTO.getRoleName()));
            UserRole userRole = userRoleRepository.findById("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c").orElse(null);
            userRoleRepository.delete(userRole);
        }
        
        
        @Test
        public void testShouldNotCreateUserWhoAlreadyExists() throws Exception {

            createUserRoleDTO.setUserId("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c112");
            createUserRole(createUserRoleDTO)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.userId").value(createUserRoleDTO.getUserId()))
                    .andExpect(jsonPath("$.roleName").value(createUserRoleDTO.getRoleName()));
            UserRole userRole = userRoleRepository.findById("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c112").orElse(null);
           MvcResult result = createUserRole(createUserRoleDTO)
                    .andExpect(status().isBadRequest())
                    .andReturn();

           assertThat(result.getResponse().getContentAsString()).isEqualTo("The user id " + createUserRoleDTO.getUserId() + "has already a role associated:");


            userRoleRepository.delete(userRole);
        }



      private ResultActions createUserRole(CreateUserRoleDTO createUserRoleDTO) throws Exception{
            return restMockMvc.perform(
                    post(API_URL + "/save")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createUserRoleDTO))
            );
        }
    }

    @Nested
    public class WhenGetting {


        @Test
        public void ShouldReturn200WhenGetAValidUser() throws Exception {
            gettingUserRole(staticUserRole.getId()).andExpect(status().isOk());


        }

        private ResultActions gettingUserRole(String userId) throws Exception{
            return restMockMvc.perform(
                    get(API_URL  + "/" + userId));
        }

    }

}
