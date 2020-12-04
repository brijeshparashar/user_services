package com.demo.user.service;

import com.demo.user.model.UserDetails;

import java.util.List;

public interface UserService {
    UserDetails getUser(Long userId);
    UserDetails updateUser(UserDetails userDetails);
}
