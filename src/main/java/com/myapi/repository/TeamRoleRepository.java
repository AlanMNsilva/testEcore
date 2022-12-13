/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.repository;


import com.myapi.models.TeamRoles;
import com.myapi.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRoleRepository extends JpaRepository<TeamRoles, Long> {

    Optional<TeamRoles> findByRoleName(String roleName);

    @Query("SELECT u, i  FROM TeamRoles i JOIN UserRole u ON i.id = u.roles.id WHERE i.roleName = :roleName")
    List<UserRole> findAllUsersByRoleName(@Param("roleName") String roleName);
}
