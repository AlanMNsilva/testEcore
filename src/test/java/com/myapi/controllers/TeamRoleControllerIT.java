/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.controllers;

import com.myapi.dto.CreateTeamRoleDTO;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserRoleControllerTest")
@Transactional
public class TeamRoleControllerIT {

    private static final String API_URL = "/api/noauth/roles/save";
    private static final String API_URL_GET_ROLES = "/api/noauth/roles/role";


    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Autowired
    protected MockMvc restMockMvc;

    private TeamRoles staticTeamRole;

    private UserRole staticUserRole;

    private final CreateTeamRoleDTO createTeamRoleDTO = TeamRolesStatic.aCreateDTO();

    @BeforeEach
    public void setup() {
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


        @Test
        public void testShouldCreateARole() throws Exception {
            createUserRole(createTeamRoleDTO)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.roleName").value(createTeamRoleDTO.getRoleName()));

        }



        private ResultActions createUserRole(CreateTeamRoleDTO dto) throws Exception{
            return restMockMvc.perform(
                    post(API_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.convertObjectToJsonBytes(dto))
            );
        }


    }
    @Nested
    public class WhenGetting {

        public void createUsers(int quantity){
            for (int x = 0; x < quantity; x++) {
                UserRole ur = UserRoles.aCreateUser();
                ur.setRoles(staticTeamRole);
                staticUserRole = userRoleRepository.saveAndFlush(ur);
            }

        }

        @Test
        public void testShouldFindUsersByRoleName() throws Exception {
            createUsers(3);
            int dataSize =  userRoleRepository.findAll().size();
            gettingUsersByRoleName(staticTeamRole.getRoleName())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(hasSize(dataSize)));

        }


        private ResultActions gettingUsersByRoleName(String roleName) throws Exception{
            return restMockMvc.perform(
                    get(API_URL_GET_ROLES).param("RoleName", roleName)
            );
        }

    }
}
