package com.demo.user.security;

import com.demo.user.dao.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserContext Object used while authenticating the user placing the request.
 */
public class UserContext implements UserDetails {

    private UserEntity userEntity;

    public UserContext(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles()
                .forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getRoleName())));
        return authorities;
    }

    /**
     * Retrieves password from database used for User authentication.
     * @return {@link String}
     */
    @Override
    public String getPassword() {
        return userEntity.getBCryptEncodedPassword();
    }

    /**
     *
     * @return {@link String}
     */
    @Override
    public String getUsername() {
        return String.valueOf(userEntity.getUserId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
