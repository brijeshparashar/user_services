package com.demo.user.dao.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Entity object to manage UserDetails in Database.
 */
@Entity
@Table(name= "UserDetail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private String bCryptEncodedPassword;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private AddressEntity address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable( name = "UserRoleMapping",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
