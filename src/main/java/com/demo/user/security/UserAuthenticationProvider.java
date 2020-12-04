package com.demo.user.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserAuthContextService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAuthenticationProvider(UserAuthContextService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        super.setUserDetailsService(this.userDetailsService);
        super.setPasswordEncoder(this.bCryptPasswordEncoder);
    }
}
