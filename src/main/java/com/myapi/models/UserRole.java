/**
 * Created by : Alan Nascimento on 12/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class UserRole {

    @Id
    private String id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="role_id")
    private TeamRoles roles;


}
