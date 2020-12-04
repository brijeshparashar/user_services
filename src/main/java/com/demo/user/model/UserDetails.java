package com.demo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * POJO for User details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Min(value = 1000000, message = "UserId should be greater than or equal to  1000000")
    @Max(value = 9999999, message = "UserId should be less than or equal to 9999999")
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String gender;
    private UserAddress address;
}
