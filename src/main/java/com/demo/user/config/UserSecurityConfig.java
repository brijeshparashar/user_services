package com.demo.user.config;

import com.demo.user.security.UserAuthContextService;
import com.demo.user.security.UserAuthenticationEntryPoint;
import com.demo.user.security.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 */
@Configuration
@EnableWebSecurity
//Method level role based security implemented.
@EnableGlobalMethodSecurity(prePostEnabled = true)
/*This class is the configuration class for Basic Auth security*/
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String ADMIN = "ADMIN";

    @Autowired
    private UserAuthenticationEntryPoint authEntryPoint;

    @Autowired
    UserAuthenticationProvider userAuthenitcationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenitcationProvider);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is to implement Basic Auth security for all the APIs.
     * The role based authorisation is implemented using the method level Annotations
     * @param http {@link HttpSecurity}
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").permitAll()
                .anyRequest()
                .authenticated().and().csrf().disable();
    }
}
