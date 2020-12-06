package com.demo.user.pact;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.demo.user.UserApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.ConfigurableWebApplicationContext;


@RunWith(SpringRestPactRunner.class)
@Provider("userProvider")
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class UserProviderContract {

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8082, "/users");

    private static ConfigurableWebApplicationContext application;

    @BeforeClass
    public static void start() {

        application = (ConfigurableWebApplicationContext)
                SpringApplication.run(UserApplication.class);
    }

    @TargetRequestFilter
    public void exampleRequestFilter(HttpRequest request) {
        request.addHeader("Authorization", "Basic MTAwMTp0ZXN0");
    }

    @State("get user")
    public void getUser() {
        log.debug("inside getUser");
    }

    @State("update user")
    public void updateUser() {
        log.debug("inside updateUser");
    }
}
