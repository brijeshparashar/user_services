package com.demo.user.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final String USER_SERVICE = "User Service";
    public static final String ACCESS_DENIED_ERROR_MESSAGE = "{\n" +
            "    \"errorCode\": \"ER003\",\n" +
            "    \"errorMessage\": \"403: Forbidden : Access is denied\",\n" +
            "    \"errors\": null\n" +
            "}";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(ACCESS_DENIED_ERROR_MESSAGE);
    }

    @Override
    public void afterPropertiesSet()  {
        setRealmName(USER_SERVICE);
        super.afterPropertiesSet();
    }

}
