/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.repository;

import com.myapi.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

}
