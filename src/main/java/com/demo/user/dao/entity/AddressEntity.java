package com.demo.user.dao.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity object for UserAddress used to manage User Address in DB.
 */
@Entity
@Table(name= "UserAddress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    private String street;
    private String city;
    private String state;
    private String postcode;

    @OneToOne(mappedBy = "address")
    private UserEntity userEntity;
}
