package com.demo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for User Address details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private String street;
    private String city;
    private String state;
    private String postcode;
}
