package com.demo.user.dao.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity object to manges Users Role in Database.
 */
@Entity
@Table(name= "UserRole")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    private String roleName;

}
