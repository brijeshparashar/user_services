package com.demo.user.controller;

import com.demo.user.model.UserDetails;
import com.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This is the controller class that exposes User end points
 */
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    public static final String APPLICATION_JSON = "application/json";
    @Autowired
    private UserService userService;


    /**
     * Retrieves the user information based on the user id.
     *
     * @param id {@link Long}
     * @return {@link UserDetails}
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON)
    @PreAuthorize("hasAnyAuthority('READ_ONLY_USER','ADMIN','READ_UPDATE_USER')")
    public UserDetails getUser(@PathVariable Long id) {
        return userService.getUser(id.longValue());
    }

    /**
     * Updates User information.
     *
     * @param userDetails {@link UserDetails}
     * @return {@link UserDetails}
     */

    @PutMapping(consumes = APPLICATION_JSON,produces = APPLICATION_JSON)
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UPDATE_USER')")
    public UserDetails updateUser(
            @RequestBody @Valid UserDetails userDetails) {
        return userService.updateUser(userDetails);
    }
}
